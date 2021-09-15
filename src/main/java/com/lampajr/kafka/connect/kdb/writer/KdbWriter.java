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

import com.lampajr.kafka.connect.kdb.sink.KdbSinkConfig;
import kx.C;
import org.apache.kafka.connect.sink.SinkRecord;

import java.io.IOException;
import java.util.List;

/**
 * Kdb implementation of a generic writer
 * TODO: implement
 */
public class KdbWriter implements Writer {

  public KdbWriter(KdbSinkConfig config) {

  }

  @Override
  public void start() {

  }

  @Override
  public void stop() {

  }

  @Override
  public void write(List<SinkRecord> records) throws IOException, C.KException {

  }

  @Override
  public void write(List<SinkRecord> records, int partition) throws IOException, C.KException {

  }

  @Override
  public Long getOffset(String topic) throws IOException, C.KException {
    return null;
  }

  @Override
  public Long getOffset(String topic, int partition) throws IOException, C.KException {
    return null;
  }
}
