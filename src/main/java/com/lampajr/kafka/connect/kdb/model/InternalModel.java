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

import java.lang.reflect.Field;

/**
 * Abstract internal model that must be returned by the parser object.
 * <p>
 * This represents the structure of a single record that must be sent to the target system (e.g., KDB).
 */
public abstract class InternalModel {

  /**
   * Converts an internal model object into an array of values.
   * Ex. new MyModel("test", 10) ==> ["test", 10]
   *
   * @return an array containing the values of the class object fields
   * @throws IllegalAccessException if something went wrong accessing model's fields
   */
  public Object[] toArray() throws IllegalAccessException {
    Field[] fields = InternalModel.super.getClass().getDeclaredFields();
    Object[] values = new Object[fields.length];

    for (int i = 0; i < values.length; i++) {
      Field f = fields[i];

      Object val = f.get(this);
      values[i] = val;
    }

    return values;
  }
}
