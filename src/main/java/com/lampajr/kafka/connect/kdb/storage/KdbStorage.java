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
package com.lampajr.kafka.connect.kdb.storage;

import kx.C;

import java.io.IOException;

/**
 * KDB storage implementation that uses the kx.C class as kdb+ driver [@see kx.C].
 */
public class KdbStorage extends Storage {

  private String host;
  private int readPort;
  private int writePort;
  private String auth;
  private boolean enableTls;

  /**
   * Save connection configurations
   *
   * @param host      kdb+ hostname
   * @param readPort  kdb+ server read port
   * @param writePort kdb+ server write port
   * @param auth      kdb+ server auth user:pass
   * @param enableTls enableTls
   */
  public KdbStorage(String host, int readPort, int writePort, String auth, boolean enableTls) {
    this.host = host;
    this.readPort = readPort;
    this.writePort = writePort;
    this.auth = auth;
    this.enableTls = enableTls;
  }

  @Override
  public void open() throws C.KException, IOException {
    readConnection = new kx.C(host, readPort, auth, enableTls);
    writeConnection = new kx.C(host, writePort, auth, enableTls);
  }
}