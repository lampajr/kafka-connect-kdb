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

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Custom KDB sink connector configuration.
 * <p>
 * It defines all properties that must be configured in order to allow the sink
 * connector to correctly interact with kdb server.
 */
public class KdbSinkConfig extends AbstractConfig {

  /**
   * Write mode enumeration, at the moment only 4 different modes are supported.
   */
  public enum WriteMode {
    /** fn[tables; data] */
    SIMPLE,
    /** fn[offset, tables; data] */
    WITH_OFFSET,
    /** fn[partition, tables; data] */
    WITH_PARTITION,
    /** fn[offset, partition, tables; data] */
    FULL
  }

  private static final Logger LOG = LoggerFactory.getLogger(KdbSinkConfig.class);

  // params prefix
  private static final String KDB_PREFIX = "kdb";

  /** Kdb hostname */
  public static final String KDB_HOST_CONFIG = KDB_PREFIX + ".host";
  private static final String KDB_HOST_DISPLAY = "Kdb Hostname";
  private static final String KDB_HOST_DOC = "The kdb+ server host to connect to.";

  /** Kdb auth */
  public static final String KDB_AUTH_CONFIG = KDB_PREFIX + ".auth";
  private static final String KDB_AUTH_DISPLAY = "Kdb Authentication";
  private static final String KDB_AUTH_DOC = "The kdb+ server authentication in the form <username>:<pwd>.\n" +
          "For example: ``myuser:mypwd``.";

  /** Kdb ssl enabled flag */
  public static final String KDB_SSL_ENABLED_CONFIG = KDB_PREFIX + ".ssl.enabled";
  private static final String KDB_SSL_ENABLED_DISPLAY = "SSL Enabled";
  private static final String KDB_SSL_ENABLED_DOC = "Flag to determine id SSL is enabled.";

  /** Kdb read port */
  public static final String KDB_PORT_READ_CONFIG = KDB_PREFIX + ".port.read";
  private static final String KDB_PORT_READ_DISPLAY = "Read Kdb Port";
  private static final String KDB_PORT_READ_DOC = "The kdb port from which read operations must be performed.";

  /** Kdb write port */
  public static final String KDB_PORT_WRITE_CONFIG = KDB_PREFIX + ".port.write";
  private static final String KDB_PORT_WRITE_DISPLAY = "Write Kdb Port";
  private static final String KDB_PORT_WRITE_DOC = "The kdb port from which write operations must be performed.";

  /** Kdb asynchronous write flag */
  public static final String KDB_ASYNC_WRITE_CONFIG = KDB_PREFIX + ".async.write";
  private static final String KDB_ASYNC_WRITE_DISPLAY = "Async Write";
  private static final String KDB_ASYNC_WRITE_DOC = "Flag to determine if the write operation must be performed " +
          "asynchronously.";

  /** Kdb calling function for writes */
  public static final String KDB_WRITE_FN_CONFIG = KDB_PREFIX + ".write.fn";
  private static final String KDB_WRITE_FN_DISPLAY = "Kdb Write Function";
  private static final String KDB_WRITE_FN_DOC = "The function that must be called performing the main operation that " +
          "writes down from kafka to the kdb server.\n" +
          "For example: ``.u.upd``.";

  /** Kdb calling function for reads (get offset operation) */
  public static final String KDB_OFFSET_FN_CONFIG = KDB_PREFIX + ".offset.fn";
  private static final String KDB_OFFSET_FN_DISPLAY = "Kdb Get Offset Function";
  private static final String KDB_OFFSET_FN_DOC = "\"The function that must be called performing read operations " +
          "(i.e., in order to get the offset - the function should follow this signature: fn(partition, tables)).\n" +
          "For example: ``getOffset``";

  /** Kdb write mode (@see WriteMode):
   * 1. fn[tables; data]
   * 2. fn[offset, tables; data]
   * 3. fn[partition, tables; data]
   * 4. fn[offset, partition, tables; data]*/
  public static final String KDB_WRITE_MODE_CONFIG = KDB_PREFIX + ".write.mode";
  private static final String KDB_WRITE_MODE_DISPLAY = "Write Mode";
  private static final String KDB_WRITE_MODE_DOC = "The write mode to use when flushing data to kdb.\n" +
          "This must be set taking into account the signature of the function defined in ``kdb.write.fn``.";

  /** Skip offset flag [if mode WITH_PARTITION or FULL] */
  public static final String KDB_SKIP_OFFSET_CONFIG = KDB_PREFIX + ".skip.offset";
  private static final String KDB_SKIP_OFFSET_DISPLAY = "Skip Kdb Offset";
  private static final String KDB_SKIP_OFFSET_DOC = "Flag to determine if the process should ignore the offset returned " +
          "by kdb itself. This must be set only if kdb.write.mode is 2. or 3.";

  /** Kdb table name */
  public static final String KDB_TABLE_NAME_CONFIG = KDB_PREFIX + ".table.name";
  private static final String KDB_TABLE_NAME_DISPLAY = "Kdb Table Name";
  private static final String KDB_TABLE_NAME_DOC = "The kdb+ table name in which the data must be flushed to.";

  private static final String CONNECTION_GROUP = "KDB Connection";

  // TODO: implement config_def .define(..).define(..)..
  public static final ConfigDef CONFIG_DEF = new ConfigDef()
          .define(
                  KDB_HOST_CONFIG,
                  ConfigDef.Type.STRING,
                  ConfigDef.NO_DEFAULT_VALUE,
                  ConfigDef.Importance.HIGH,
                  KDB_HOST_DOC,
                  CONNECTION_GROUP,
                  1,
                  ConfigDef.Width.LONG,
                  KDB_HOST_DISPLAY
          ); // keep going setting defines

  // TODO: Add local variables to store custom kdb configurations

  public KdbSinkConfig(Map<?, ?> props) {
    super(CONFIG_DEF, props);
  }
}
