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
package com.lampajr.kafka.connect.kdb.utils;

import com.lampajr.kafka.connect.kdb.sink.KdbSinkConfig;

import java.util.HashMap;
import java.util.Map;

public class TestUtils {

  public static KdbSinkConfig createConfigs() {
    Map<String, Object> props = new HashMap<>();

    // required
    props.put(KdbSinkConfig.KDB_HOST_CONFIG, "127.0.0.1");
    props.put(KdbSinkConfig.KDB_AUTH_CONFIG, "username:pass");
    props.put(KdbSinkConfig.KDB_PORT_WRITE_CONFIG, 8080);
    props.put(KdbSinkConfig.KDB_PORT_READ_CONFIG, 8081);
    props.put(KdbSinkConfig.KDB_WRITE_FN_CONFIG, ".u.upd");
    props.put(KdbSinkConfig.KDB_TABLE_NAME_CONFIG, "tableName");

    return new KdbSinkConfig(props);
  }
}
