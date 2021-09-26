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
import com.lampajr.kafka.connect.kdb.util.BaseLogger;
import kx.C;
import org.apache.kafka.connect.sink.SinkRecord;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * Generic writer abstract class.
 * <p>
 * Each subclass must implement the following methods:
 * * start(): start the writer instance, loading all needed configuration and dependencies.
 * * stop(): stop everything, closing all opened resources.
 * * write(...): flushes data to the target system.
 * * getOffset(...): retrieve kafka offset that are recorded on the target system.
 */
public abstract class Writer extends BaseLogger {

  /**
   * Dynamically load a parser object
   *
   * @param className fullname of the parser class
   * @return a new instance of the loaded parser
   */
  protected Parser<?> loadParser(String className) {
    logger.info("Loading parser class: {}", className);
    ClassLoader loader = Writer.class.getClassLoader();

    try {
      return (Parser<?>) loader.loadClass(className).getDeclaredConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException
        | ClassNotFoundException e) {
      // break connector initialization
      throw new RuntimeException("Error loading parser class", e);
    }
  }

  /**
   * Initialize the writer instance, loading all needed dependencies
   */
  public abstract void init();

  /**
   * Start the writer instance, acquiring all resources from its dependencies.
   * It generally requires that init() has been already called.
   *
   * @throws C.KException error occurred in the remote q process
   * @throws IOException  error occurred in the communication with remote server
   */
  public abstract void start() throws C.KException, IOException;

  /**
   * Stops the writer releasing all the acquired resources.
   *
   * @throws IOException error occurred in the communication with remote server
   */
  public abstract void stop() throws IOException;

  /**
   * Flushes data to a target system
   *
   * @param records   list of records to store
   * @param partition not null if the q process expects the topic partition
   * @param offset    not null if the q process expects the offset
   * @throws IOException  if something went wrong in the connection with the target system
   * @throws C.KException if the target kdb system throws some errors
   */
  public abstract void write(Collection<SinkRecord> records,
                             Integer partition,
                             Long offset) throws IOException, C.KException;

  /**
   * Retrieve the offset for a specific topic
   *
   * @param topic kafka topic
   * @return the last saved offset, -1 if never set
   * @throws IOException  if something went wrong in the connection with the target system
   * @throws C.KException if the target kdb system throws some errors
   */
  public abstract Long getOffset(String topic) throws IOException, C.KException;

  /**
   * Retrieve the offset for a specific topic and partition
   *
   * @param topic     kafka topic
   * @param partition topic partition
   * @return the last saved offset, -1 if never set
   * @throws IOException  if something went wrong in the connection with the target system
   * @throws C.KException if the target kdb system throws some errors
   */
  public abstract Long getOffset(String topic, int partition) throws IOException, C.KException;
}
