/*
 * Copyright 2012-2014 eBay Software Foundation and ios-driver committers
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.uiautomation.ios.e2e.config.launching;


import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.uiautomation.ios.communication.device.DeviceVariation;
import org.uiautomation.ios.utils.ClassicCommands;
import org.uiautomation.ios.utils.IOSVersion;

import java.util.List;

public class Xcode6SafariLauncherTest extends SafariLauncherTest {

  @Override
  public void checkSetup() {

    IOSVersion version = ClassicCommands.getXCodeVersion();
    Assert.assertTrue(version.equals("6.0.1"));
    List<String> sdks = ClassicCommands.getInstalledSDKs();
    Assert.assertTrue(sdks.contains("7.0.3"));
    Assert.assertTrue(sdks.contains("7.1"));
    Assert.assertTrue(sdks.contains("8.0"));
  }


  @DataProvider
  @Override
  public Object[][] sdkAndSafariVersion() {
    Object[][] res = new Object[][]{
        //http://openradar.appspot.com/radar?id=5221532267708416
        //{"7.0.3", "9537.53"}, broken

        {"7.1", "9537.53", DeviceVariation.iPhone4s},
        {"7.1", "9537.53", DeviceVariation.iPhone5},
        {"7.1", "9537.53", DeviceVariation.iPhone5s},

        {"8.0", "600.1.4", DeviceVariation.iPhone4s},
        {"8.0", "600.1.4", DeviceVariation.iPhone5},
        {"8.0", "600.1.4", DeviceVariation.iPhone5s},
        {"8.0", "600.1.4", DeviceVariation.iPhone6},
        {"8.0", "600.1.4", DeviceVariation.iPhone6Plus},
    };
    return res;
  }

  @DataProvider
  @Override
  public Object[][] versionToSDK() {
    Object[][] res = new Object[][]{
        {"6.1", "8536.25"},
        {"7.1", "9537.53"}
    };
    return res;
  }

  @DataProvider
  @Override
  public Object[][] invalid() {
    Object[][] res = new Object[][]{
        {"7.1", "8536.25"},
        {"7.0.3", "8536.25"},
        {"6.0", "9537.53"},
        {"6.1", "9537.53"},
    };
    return res;
  }
}
