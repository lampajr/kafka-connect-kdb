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
import kx.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Abstract storage which is in charge to manage the connection with the target kdb server.
 * <p>
 * Its implementation is mainly based on the Kdb driver usage [@see kx.C]
 */
public abstract class Storage {

  /**
   * logger
   */
  protected final Logger logger = LoggerFactory.getLogger(getClass());

  /**
   * Represents a kdb+ remote write connection
   */
  protected Connection writeConnection;

  /**
   * Represents a kdb+ remote read connection
   */
  protected Connection readConnection;

  /**
   * Function that opens all needed connections
   *
   * @throws C.KException error occurred in the remote q process
   * @throws IOException  error occurred in the communication with remote server
   */
  public abstract void open() throws C.KException, IOException;

  /**
   * Function that closes all opened connections
   *
   * @throws IOException error occurred in the communication with remote server
   */
  public abstract void close() throws IOException;

  /**
   * Synchronously invokes a q function to the target kdb+ server using the connection object
   *
   * @param fn     kdb+/q function
   * @param params fn params
   * @return the sync object returned by the q function
   * @throws C.KException error occurred in the remote q process
   * @throws IOException  error occurred in the communication with remote server
   */
  public Object invoke(String fn, Object... params) throws C.KException, IOException {
    Object[] paramArray = {fn.toCharArray(), params};
    return writeConnection.k(fn, paramArray);
  }

  /**
   * Asynchronously invokes a q function to the target kdb+ server using the connection object
   *
   * @param fn     kdb+/q function
   * @param params fn params
   * @throws IOException error occurred in the communication with remote server
   */
  public void invokeAsync(String fn, Object... params) throws IOException {
    Object[] paramArray = {fn.toCharArray(), params};
    writeConnection.ks(fn, paramArray);
  }

  /**
   * Synchronously invokes a q function to the target kdb+ server using the connection
   * object aiming to retrieve some data
   *
   * @param fn     kdb+/q function
   * @param params fn params
   * @return the sync object returned by the q function
   * @throws C.KException error occurred in the remote q process
   * @throws IOException  error occurred in the communication with remote server
   */
  public Object read(String fn, Object... params) throws C.KException, IOException {
    Object[] paramArray = {fn.toCharArray(), params};
    return readConnection.k(fn, paramArray);
  }
}
