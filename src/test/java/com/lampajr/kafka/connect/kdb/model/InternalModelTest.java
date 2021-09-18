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
package com.lampajr.kafka.connect.kdb.model;

import com.lampajr.kafka.connect.kdb.AbstractTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class InternalModelTest extends AbstractTest {

  // class example that extends the generic internal model
  static class MyModel extends InternalModel {
    String string;
    int intNumber;
    long longNumber;
    float floatNumber;
    double doubleNumber;
    char character;

    public MyModel(String string, int intNumber, long longNumber, float floatNumber, double doubleNumber, char character) {
      this.string = string;
      this.intNumber = intNumber;
      this.longNumber = longNumber;
      this.floatNumber = floatNumber;
      this.doubleNumber = doubleNumber;
      this.character = character;
    }
  }

  @Test
  public void toArrayTest() {
    InternalModel m = new MyModel("test", 10, 829759843579872L, 1.5F, 0.389275, 'x');
    Assert.assertEquals("[test, 10, 829759843579872, 1.5, 0.389275, x]", Arrays.toString(m.toArray()));
  }
}