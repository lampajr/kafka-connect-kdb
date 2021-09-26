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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import support.BaseDockerIntegrationTest;

import java.io.IOException;

public class KxConnectionIT extends BaseDockerIntegrationTest {

  @Before
  public void setUp() throws C.KException, IOException {
    logger.info("Setting up kx connection {}:{};{};{}", kdbHost, kdbPort, kdbAuth, enableTls);
    kxConn = new C(kdbHost, kdbPort, kdbAuth, enableTls);
    Assert.assertNotNull(kxConn);
  }

  @Test
  public void writeSyncTest() throws C.KException, IOException {
    //Create typed arrays for holding data
    String[] sym = new String[] {"IBM"};
    double[] bid = new double[] {100.25};
    double[] ask = new double[] {100.26};
    int[] bSize = new int[] {1000};
    int[] aSize = new int[] {1000};
    //Create Object[] for holding typed arrays
    Object[] data = new Object[] {sym, bid, ask, bSize, aSize};

    // use sync writes since we can check if exceptions are thrown
    kxConn.k(".u.upd", "quote", data);
  }

  @Test
  public void throwLengthErrorTest() throws IOException {
    //Create typed arrays for holding data
    String[] sym = new String[] {"IBM"};
    double[] bid = new double[] {100};
    double[] ask = new double[] {100.26};
    int[] bSize = new int[] {1000};
    // provide a wrong number of params, 1 less
    int[] aSize = new int[] {1000};
    //Create Object[] for holding typed arrays
    Object[] data = new Object[] {sym, bid, ask, bSize};

    // use sync writes since we can check if exceptions are thrown
    try {
      kxConn.k(".u.upd", "quote", data);
      Assert.fail();
    } catch (C.KException e) {
      Assert.assertEquals("length", e.getMessage());
    }
  }

  @Test
  public void showTable() throws IOException, C.KException {
    kxConn.k("{x+y;show quote}", 1,2);
  }

}