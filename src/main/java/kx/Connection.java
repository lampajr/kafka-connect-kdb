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
package kx;

import java.io.Closeable;
import java.io.IOException;

/**
 * Abstract interface that models a generic connection with a kdb+ remote server
 */
public interface Connection extends Closeable {

  /**
   * Asynchronous kdb+ function invocation
   *
   * @param fn     function name that must be invoked
   * @param params array of object params
   * @throws IOException error occurred in the communication with remote server
   */
  void ks(String fn, Object params) throws IOException;

  /**
   * Synchronous kdb+ function invocation
   *
   * @param fn     function name that must be invoked
   * @param params array of object params
   * @return function result
   * @throws C.KException error occurred in the remote q process
   * @throws IOException  error occurred in the communication with remote server
   */
  Object k(String fn, Object params) throws C.KException, IOException;
}
