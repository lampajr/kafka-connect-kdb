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

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class KxConnectionIT {

  private String host = "127.0.0.1";
  private int port = 5010;
  private C kxConn;

  @Before
  public void setUp() throws Exception {
    kxConn = new C(host, port);
  }

  @Test
  public void writeAsyncTest() {

  }

}