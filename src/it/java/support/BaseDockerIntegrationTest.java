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
package support;

import com.palantir.docker.compose.DockerComposeRule;
import kx.C;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Abstract integration test that uses a dockerized container.
 * dockerRule initializes:
 * - Tickerplant kdb+ architecture
 * - Kafka server [TODO]
 */
public abstract class BaseDockerIntegrationTest {

  protected final Logger logger = LoggerFactory.getLogger(getClass());

  // TODO: fix this path
  private static final String dockerRuleFilename = "target/test-classes/docker-compose-kdb-it.yml";

  // connection properties [TODO: use application-test.properties]
  protected String kdbHost = "127.0.0.1";
  protected int kdbPort = 5010;
  protected String kdbAuth = "user:pass";
  protected boolean enableTls = false;

  // KX connection
  protected C kxConn;

  public static DockerComposeRule dockerRule = DockerComposeRule.builder()
      .file(dockerRuleFilename)
      .build();

  @BeforeClass
  public static void beforeAll() throws IOException, InterruptedException {
    // starting docker cluster
    dockerRule.before();
  }

  @AfterClass
  public static void afterAll() {
    dockerRule.after();
  }
}
