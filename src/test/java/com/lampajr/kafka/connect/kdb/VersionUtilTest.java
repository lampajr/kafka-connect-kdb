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
package com.lampajr.kafka.connect.kdb;

import com.lampajr.kafka.connect.kdb.utils.BaseTest;
import org.junit.Assert;
import org.junit.Test;

public class VersionUtilTest extends BaseTest {

    @Test
    public void getVersion() {
        Assert.assertEquals(VersionUtil.FALLBACK_VERSION, VersionUtil.getVersion(VersionUtil.class));
    }
}