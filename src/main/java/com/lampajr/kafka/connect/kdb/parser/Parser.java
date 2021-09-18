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
package com.lampajr.kafka.connect.kdb.parser;

import com.lampajr.kafka.connect.kdb.model.InternalModel;

import java.util.List;

/**
 * This class represent a generic parser that converts a byte array (received from kafka) into
 * a KDB class object [@see InternalModel].
 * The result of this parser will be sent to the KDB.
 * A single kafka record might produce multiple KDB records.
 * <p>
 * O: represents the output model, it must extend InternalModel
 */
public interface Parser<O extends InternalModel> {

  /**
   * Parse a kafka record into one or multiple KDB records.
   *
   * @param input Array[Byte] representing a single deserialized kafka record
   * @return a list of KDB records
   */
  List<O> parse(Byte[] input);
}
