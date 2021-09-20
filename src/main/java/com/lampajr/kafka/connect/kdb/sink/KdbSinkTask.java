/*
 * Copyright © 2021 Andrea Lamparelli (a.lamparelli95@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lampajr.kafka.connect.kdb.sink;

import com.lampajr.kafka.connect.kdb.VersionUtil;
import com.lampajr.kafka.connect.kdb.writer.KdbWriter;
import com.lampajr.kafka.connect.kdb.writer.Writer;
import kx.C;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.connect.errors.RetriableException;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Main KDB sink task
 * TODO: implement
 */
public class KdbSinkTask extends SinkTask {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  // kdb sink configuration
  private KdbSinkConfig config;

  private Map<TopicPartition, Long> offsetMap = new HashMap<>();

  // writer: flushes data to kdb
  private Writer writer;

  private boolean usingOffset;
  private boolean usingPartition;

  @Override
  public String version() {
    return VersionUtil.getVersion(getClass());
  }

  @Override
  public void start(final Map<String, String> props) {
    logger.info("Starting KDB Sink task..");

    // init configurations
    config = new KdbSinkConfig(props);

    usingOffset = isUsingOffset();
    usingPartition = isUsingPartition();

    // init writer
    try {
      initWriter();
    } catch (C.KException e) {
      throw new RuntimeException("Kdb+ server connection failed due to a kx error.", e);
    } catch (IOException e) {
      throw new RetriableException("Unable to establish the kdb+ server connection, retrying...");
    }

    // load offset only if enabled
    if (usingOffset) {
      loadOffsets(this.context.assignment());
    }
  }

  @Override
  public void put(Collection<SinkRecord> records) {
    logger.info("Processing {} record(s)", records.size());

    if (records.isEmpty()) {
      return;
    }

    try {
      writer.write(records, usingPartition, usingOffset);
    } catch (IOException e) {
      throw new RetriableException("Error flushing data to kdb, retrying..", e);
    } catch (C.KException e) {
      throw new RuntimeException("KDB+ writes failed deu to kx error", e);
    }

  }

  @Override
  public void stop() {
    logger.info("Stopping KDB {} Sink task..", config.connectorName);
    try {
      writer.stop();
    } catch (IOException e) {
      throw new RuntimeException("Error stopping writer.", e);
    }
  }

  /**
   * TODO: implement
   * Initialize the kdb writer service which
   * main goal is to flush data to the kdb server
   */
  private void initWriter() throws C.KException, IOException {
    // create instance
    writer = new KdbWriter(this.config);
    writer.init(); // initialize the writer
    writer.start(); // start the writer
  }

  /**
   * Load the offset for all topic partitions
   *
   * @param partitions set of topic partitions
   */
  private void loadOffsets(Set<TopicPartition> partitions) {
    logger.info("Loading offsets..");
    offsetMap.clear();

    for (TopicPartition partition : partitions) {
      try {
        Long offset = usingPartition
            ? writer.getOffset(partition.topic(), partition.partition())
            : writer.getOffset(partition.topic());

        logger.info("Retrieved offset <{}> for topic <{}> and partition <{}>.",
            offset, partition.topic(), partition.partition());

        if (offset > -1) {
          offsetMap.put(partition, offset + 1);
        }
      } catch (C.KException | IOException e) {
        logger.error(String.format("Error retrieving offset for topic %s.", partition.topic()), e);
      }
    }
  }

  /**
   * Return true if skipDbOffset is set to false and the writeMode is either WITH_OFFSET or FULL
   *
   * @return true if the offset is managed in kdb, false otherwise
   */
  private boolean isUsingOffset() {
    return !this.config.skipOffset &&
        (this.config.writeMode == KdbSinkConfig.WriteMode.WITH_OFFSET
            || this.config.writeMode == KdbSinkConfig.WriteMode.FULL);
  }

  /**
   * Return true if the system is managed to handle the partition also on kdb side,
   * i.e., if the writeMode is either WITH_PARTITION or FULL
   *
   * @return true if the partition is managed in kdb, false otherwise
   */
  private boolean isUsingPartition() {
    return this.config.writeMode == KdbSinkConfig.WriteMode.WITH_PARTITION
        || this.config.writeMode == KdbSinkConfig.WriteMode.FULL;
  }

}
