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

import kx.C;
import org.apache.kafka.connect.sink.SinkRecord;

import java.io.IOException;
import java.util.List;

public interface Writer {

  /**
   * Start the writer
   */
  void start();

  /**
   * Stops the writer
   */
  void stop();

  /**
   * Flushes data to a target system
   *
   * @param records list of records to store
   */
  void write(List<SinkRecord> records) throws IOException, C.KException;

  /**
   * Flushes data coming from a specific partition to a target system
   *
   * @param records   list of records to store
   * @param partition topic partition from which data has been retrieved
   */
  void write(List<SinkRecord> records, int partition) throws IOException, C.KException;

  /**
   * Retrieve the offset for a specific topic
   *
   * @param topic kafka topic
   * @return the last saved offset, -1 if never set
   */
  Long getOffset(String topic) throws IOException, C.KException;

  /**
   * Retrieve the offset for a specific topic and partition
   *
   * @param topic     kafka topic
   * @param partition topic partition
   * @return the last saved offset, -1 if never set
   */
  Long getOffset(String topic, int partition) throws IOException, C.KException;
}
