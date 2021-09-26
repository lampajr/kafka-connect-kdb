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
package com.lampajr.kafka.connect.kdb.writer;

import com.lampajr.kafka.connect.kdb.model.InternalModel;
import com.lampajr.kafka.connect.kdb.parser.Parser;
import com.lampajr.kafka.connect.kdb.utils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class WriterTest {

  public static class CustomModel extends InternalModel {
    String name;
    int number;

    public CustomModel(String name, int number) {
      this.name = name;
      this.number = number;
    }
  }

  public static class CustomParser implements Parser<CustomModel> {

    @Override
    public List<CustomModel> parse(byte[] input) {
      return new ArrayList<>();
    }
  }

  private Writer writer;

  @Before
  public void setUp() {
    writer = new KdbWriter(TestUtils.createConfigs());
  }

  @Test
  public void loadParser() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    String className = CustomParser.class.getName();
    Parser<?> parser = writer.loadParser(className);
    Assert.assertTrue(parser instanceof CustomParser);
    CustomParser customParser = (CustomParser) parser;
    Assert.assertTrue(customParser.parse(new byte[] {}).isEmpty());
  }
}