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

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Version utility class
 */
public class VersionUtil {

  private static final Logger log = LoggerFactory.getLogger(VersionUtil.class);

  final static String FALLBACK_VERSION = "0.0.0.0";

  /**
   * Get the version of the specified class
   * @param cls class
   * @return version
   */
  public static String getVersion(Class<?> cls) {
    String version;

    try {
      version = cls.getPackage().getImplementationVersion();

      return Strings.isNullOrEmpty(version) ? FALLBACK_VERSION : version;
    } catch (Exception ex) {
      log.error("Exception thrown while retrieving version", ex);
      return FALLBACK_VERSION;
    }
  }
}
