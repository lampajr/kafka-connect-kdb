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
package com.lampajr.kafka.connect.kdb;

import com.lampajr.kafka.connect.kdb.sink.KdbSinkConfig;
import com.lampajr.kafka.connect.kdb.sink.KdbSinkTask;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.sink.SinkConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KdbSinkConnector extends SinkConnector {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private Map<String, String> configProps;

  @Override
  public void start(Map<String, String> props) {
    this.configProps = props;
  }

  @Override
  public Class<? extends Task> taskClass() {
    return KdbSinkTask.class;
  }

  @Override
  public List<Map<String, String>> taskConfigs(int maxTasks) {
    logger.info("Setting task configurations for {} workers.", maxTasks);

    final List<Map<String, String>> configs = new ArrayList<>(maxTasks);
    for (int i = 0; i < maxTasks; ++i) {
      configs.add(configProps);
    }
    return configs;
  }

  @Override
  public void stop() {
    // do nothing
  }

  @Override
  public ConfigDef config() {
    return KdbSinkConfig.CONFIG_DEF;
  }

  @Override
  public String version() {
    return VersionUtil.getVersion(getClass());
  }
}
