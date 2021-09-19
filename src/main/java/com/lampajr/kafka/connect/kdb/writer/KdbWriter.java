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
package com.lampajr.kafka.connect.kdb.writer;

import com.lampajr.kafka.connect.kdb.parser.Parser;
import com.lampajr.kafka.connect.kdb.sink.KdbSinkConfig;
import com.lampajr.kafka.connect.kdb.storage.KdbStorage;
import com.lampajr.kafka.connect.kdb.storage.Storage;
import kx.C;
import org.apache.kafka.connect.sink.SinkRecord;

import java.io.IOException;
import java.util.List;

/**
 * Kdb implementation of a generic writer.
 * <p>
 * This writer manages all the required steps to flush the data to the remote kdb server:
 * 1. startup:
 * * load the specific parsers instance
 * * load the specific kdb storage instance
 * 2. writes:
 * * receive a list of sink records
 * * parse byte array (single records) into some intermediate representation
 * * transform an intermediate representation into an internal model
 * * flushes data to the kdb server using the kdb storage
 */
public class KdbWriter extends Writer {

  private final Storage storage;

  private final Parser<?> parser;

  /**
   * Creates a KDB writer instance starting from KDB sink connector configuration
   *
   * @param config connector configuration
   */
  public KdbWriter(KdbSinkConfig config) throws C.KException, IOException {
    // in according to the provided configuration, use the correct storage instance
    storage = new KdbStorage(
        config.kdbHost,
        config.kdbReadPort,
        config.kdbWritePort,
        config.kdbAuth,
        false
    );

    // dynamically load a parser class in according to the provided configuration
    parser = null;
  }

  @Override
  public void start() {
    logger.info("Starting kdb writer..");
    // try opening storage connection
  }

  @Override
  public void stop() {
    logger.info("Stopping kdb writer..");
    // close storage connection
  }

  @Override
  public void write(List<SinkRecord> records) throws IOException, C.KException {

  }

  @Override
  public void write(List<SinkRecord> records, int partition) throws IOException, C.KException {

  }

  @Override
  public Long getOffset(String topic) throws IOException, C.KException {
    return -1L;
  }

  @Override
  public Long getOffset(String topic, int partition) throws IOException, C.KException {
    return -1L;
  }
}
