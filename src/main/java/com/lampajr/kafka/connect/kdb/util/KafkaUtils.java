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
package com.lampajr.kafka.connect.kdb.util;

import org.apache.kafka.connect.sink.SinkRecord;

import java.util.Map;

/**
 * Kafka utilities class
 */
public class KafkaUtils extends BaseLogger {
  /**
   * Connector name config key
   */
  public static final String CONNECTOR_NAME_CONFIG = "name";

  /**
   * Retrieve the connector name from the configuration props map
   *
   * @param props configs
   * @return the connector's name
   */
  public static String getConnectorName(Map<?, ?> props) {
    Object name = props.get(CONNECTOR_NAME_CONFIG);
    return name == null ? null : name.toString();
  }

  /**
   * Compute the location of a single sink record
   *
   * @param record sink record to process
   * @return the location in terms of topic, partition and offset
   */
  public static String formatLocation(SinkRecord record) {
    return String.format(
        "topic = %s partition = %s offset = %s",
        record.topic(),
        record.kafkaPartition(),
        record.kafkaOffset()
    );
  }
}
