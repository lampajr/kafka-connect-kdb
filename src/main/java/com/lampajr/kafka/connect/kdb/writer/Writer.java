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
import java.util.List;

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
   * @throws ClassNotFoundException    dynamic parser class not found
   * @throws NoSuchMethodException     dynamic parser class not found
   * @throws InvocationTargetException dynamic parser class not found
   * @throws InstantiationException    dynamic parser class not found
   * @throws IllegalAccessException    dynamic parser class not found
   */
  protected Parser<?> loadParser(String className) throws ClassNotFoundException, NoSuchMethodException,
      InvocationTargetException, InstantiationException, IllegalAccessException {
    logger.info("Loading parser class: {}", className);
    ClassLoader loader = Writer.class.getClassLoader();
    return (Parser<?>) loader.loadClass(className).getDeclaredConstructor().newInstance();
  }

  /**
   * Start the writer
   */
  public abstract void start();

  /**
   * Stops the writer
   */
  public abstract void stop();

  /**
   * Flushes data to a target system
   *
   * @param records list of records to store
   * @throws IOException  if something went wrong in the connection with the target system
   * @throws C.KException if the target kdb system throws some errors
   */
  public abstract void write(List<SinkRecord> records) throws IOException, C.KException;

  /**
   * Flushes data coming from a specific partition to a target system
   *
   * @param records   list of records to store
   * @param partition topic partition from which data has been retrieved
   * @throws IOException  if something went wrong in the connection with the target system
   * @throws C.KException if the target kdb system throws some errors
   */
  public abstract void write(List<SinkRecord> records, int partition) throws IOException, C.KException;

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
