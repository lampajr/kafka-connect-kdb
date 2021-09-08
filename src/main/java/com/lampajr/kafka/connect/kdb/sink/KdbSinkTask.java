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
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;

/**
 * Main KDB sink task
 * TODO: implement
 */
public class KdbSinkTask extends SinkTask {

  private static Logger logger = LoggerFactory.getLogger(KdbSinkTask.class);

  @Override
  public String version() {
    return VersionUtil.getVersion(getClass());
  }

  @Override
  public void start(Map<String, String> map) {

  }

  @Override
  public void put(Collection<SinkRecord> collection) {

  }

  @Override
  public void stop() {

  }
}
