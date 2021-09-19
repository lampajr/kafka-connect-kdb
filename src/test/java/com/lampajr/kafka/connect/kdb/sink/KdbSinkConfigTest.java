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

import com.lampajr.kafka.connect.kdb.utils.BaseTest;
import org.apache.kafka.common.config.ConfigException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class KdbSinkConfigTest extends BaseTest {

  private KdbSinkConfig config;
  private Map<String, Object> props;

  @Before
  public void setUp() {
    props = new HashMap<>();
  }

  @Test(expected = ConfigException.class)
  public void missingRequiredConfigTest() {
    Assert.assertTrue(props.isEmpty());
    setupConfigs();
  }

  @Test
  public void missingKdbHostConfigTest() {
    Assert.assertTrue(props.isEmpty());
    try {
      setupConfigs();
    } catch (ConfigException ex) {
      Assert.assertEquals("Missing required configuration \"kdb.host\" which has no default value.", ex.getMessage());
    }
  }

  @Test
  public void missingRequiredOffsetFnTest() {
    setupRequiredProps();
    // use mode that requires offset -> offsetFn becomes mandatory
    props.put(KdbSinkConfig.KDB_WRITE_MODE_CONFIG, "FULL");
    try {
      setupConfigs();
    } catch (ConfigException ex) {
      Assert.assertEquals("Offset function is mandatory with FULL or WITH_OFFSET write mode!", ex.getMessage());
    }
  }

  @Test
  public void checkDefaultParamsTest() {
    setupRequiredProps();
    setupConfigs();

    // checks
    checkDefaultParams();
  }

  @Test
  public void checkProvidedParamsTest() {
    setupRequiredProps();
    setupConfigs();

    // checks
    checkProvidedParams();
  }

  @Test
  public void checkParamsChangingWriteModeTest() {
    setupRequiredProps();
    props.put(KdbSinkConfig.KDB_WRITE_MODE_CONFIG, "with_offset");
    props.put(KdbSinkConfig.KDB_OFFSET_FN_CONFIG, "getOffset");
    setupConfigs();

    // checks
    checkProvidedParams();
    Assert.assertEquals(KdbSinkConfig.WriteMode.WITH_OFFSET, config.writeMode);
    Assert.assertEquals("getOffset", config.offsetFn);
  }

  private void checkDefaultParams() {
    Assert.assertEquals(KdbSinkConfig.KDB_ASYNC_WRITE_DEFAULT, config.asyncWrite);
    Assert.assertEquals(KdbSinkConfig.KDB_SKIP_OFFSET_DEFAULT, config.skipOffset);
    Assert.assertEquals(KdbSinkConfig.KDB_SSL_ENABLED_DEFAULT, config.sslEnabled);
    Assert.assertEquals(KdbSinkConfig.KDB_WRITE_MODE_DEFAULT, config.writeMode);
  }

  private void checkProvidedParams() {
    Assert.assertEquals("127.0.0.1", config.kdbHost);
    Assert.assertEquals("username:pass", config.kdbAuth);
    Assert.assertEquals(Long.valueOf(8080), config.kdbWritePort);
    Assert.assertEquals(Long.valueOf(8081), config.kdbReadPort);
    Assert.assertEquals(".u.upd", config.writeFn);
    Assert.assertEquals("tableName", config.tableName);
  }

  private void setupConfigs() {
    config = new KdbSinkConfig(props);
  }

  private void setupRequiredProps() {
    props.put(KdbSinkConfig.KDB_HOST_CONFIG, "127.0.0.1");
    props.put(KdbSinkConfig.KDB_AUTH_CONFIG, "username:pass");
    props.put(KdbSinkConfig.KDB_PORT_WRITE_CONFIG, 8080);
    props.put(KdbSinkConfig.KDB_PORT_READ_CONFIG, 8081);
    props.put(KdbSinkConfig.KDB_WRITE_FN_CONFIG, ".u.upd");
    props.put(KdbSinkConfig.KDB_TABLE_NAME_CONFIG, "tableName");
    // fake parser
    props.put(KdbSinkConfig.KDB_PARSER_CLASS_CONFIG, "com.lampajr.kafka.connect.kdb.ExampleParser");
  }
}