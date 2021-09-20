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

import com.google.common.base.Preconditions;
import com.lampajr.kafka.connect.kdb.parser.Parser;
import com.lampajr.kafka.connect.kdb.sink.KdbSinkConfig;
import com.lampajr.kafka.connect.kdb.storage.KdbStorage;
import com.lampajr.kafka.connect.kdb.storage.Storage;
import kx.C;
import org.apache.kafka.connect.errors.DataException;
import org.apache.kafka.connect.sink.SinkRecord;

import java.io.IOException;
import java.util.Collection;

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

  private final KdbSinkConfig config;

  private Storage storage;

  private Parser<?> parser;

  private boolean isAsync;

  private String writeFn;

  private String offsetFn;

  private KdbSinkConfig.WriteMode writeMode;

  /**
   * Creates a KDB writer instance saving the provided configurations
   *
   * @param config connector configuration
   */
  public KdbWriter(KdbSinkConfig config) {
    this.config = config;
  }

  @Override
  public void init() {
    logger.info("Initializing kdb writer..");

    isAsync = this.config.asyncWrite;
    writeFn = this.config.writeFn;
    offsetFn = this.config.offsetFn;
    writeMode = this.config.writeMode;

    // in according to the provided configuration, use the correct storage instance
    storage = new KdbStorage(
        config.kdbHost,
        config.kdbReadPort,
        config.kdbWritePort,
        config.kdbAuth,
        false
    );

    // dynamically load a parser class in according to the provided configuration
    parser = loadParser(this.config.parserClassName);
  }

  @Override
  public void start() throws C.KException, IOException {
    logger.info("Starting kdb writer..");
    Preconditions.checkState(storage != null,
        "The storage is null, have you initialized the writer?");
    Preconditions.checkState(parser != null,
        "The parser is null, have you initialized the writer?");

    // try opening storage connection
    storage.open();
  }

  @Override
  public void stop() throws IOException {
    logger.info("Stopping kdb writer..");
    // close storage connection
    storage.close();
  }

  @Override
  public void write(Collection<SinkRecord> records, boolean usingPartition, boolean usingOffset)
      throws IOException, C.KException {
    // null means to used
    Integer partition = null;
    Long offset = null;
    byte[] data = new byte[] {};

    if (usingPartition) {
      // retrieve partition
    }

    if (usingOffset) {
      // retrieve offset
    }

    store(writeFn, offset, partition, data);
  }

  @Override
  public Long getOffset(String topic) throws IOException, C.KException {
    // TODO: implement
    return -1L;
  }

  @Override
  public Long getOffset(String topic, int partition) throws IOException, C.KException {
    // TODO: implement
    return -1L;
  }

  /**
   * Flushes data to kdb+
   *
   * @param fn        q function
   * @param offset    kafka offset [nullable]
   * @param partition topic partition [nullable]
   * @param data      data that must be stored
   * @throws C.KException error occurred in q process
   * @throws IOException  error occurred in the communication
   */
  private void store(String fn, Long offset, Integer partition, byte[] data) throws C.KException, IOException {
    Object[] params = {};

    if (offset != null) {
      params = new Object[] {params, offset};
    }

    if (partition != null) {
      params = new Object[] {params, partition};
    }

    params = new Object[] {params, data};

    if (isAsync) {
      storage.invokeAsync(fn, params);
    } else {
      storage.invoke(fn, params);
    }
  }

  /**
   * Deserialize the object into a byte array
   *
   * @param source sink record params (e.g., value or key)
   * @param input  the object to deserialize
   * @return the byte array representation of the input
   * @throws DataException if the input has an invalid format
   */
  private byte[] toBytes(String source, Object input) {
    final byte[] result;

    if (input instanceof byte[]) {
      result = (byte[]) input;
    } else if (null == input) {
      result = null;
    } else {
      throw new DataException(
          String.format(
              "The %s for the record must be in Bytes format. Consider using the ByteArrayConverter.",
              source
          )
      );
    }

    return result;
  }
}
