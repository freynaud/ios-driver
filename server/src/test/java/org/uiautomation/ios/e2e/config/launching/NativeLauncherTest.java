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


import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.uiautomation.ios.BaseIOSDriverTest;
import org.uiautomation.ios.IOSCapabilities;
import org.uiautomation.ios.client.uiamodels.impl.RemoteIOSDriver;
import org.uiautomation.ios.communication.device.DeviceVariation;

public abstract class NativeLauncherTest extends BaseIOSDriverTest {

  private final static String version = "2.10";

  @BeforeClass
  public abstract void checkSetup();

  @AfterMethod(alwaysRun = true)
  public void afterMethod() throws Exception {
    stopDriver();
  }

  // list of all the valid {sdk }
  @DataProvider
  public abstract Object[][] sdk();


  @DataProvider
  public abstract Object[][] wrongCombination();


  @Test(dataProvider = "sdk")
  public void supportAllInstalledSDKs(String sdk, DeviceVariation variation) {
    try {
      IOSCapabilities cap = IOSCapabilities.iphone("UICatalog");
      cap.setSDKVersion(sdk);
      cap.setDeviceVariation(variation);
      driver = new RemoteIOSDriver(getRemoteURL(), cap);
      IOSCapabilities actual = driver.getCapabilities();

      Assert.assertEquals(actual.getSDKVersion(), sdk);
      Assert.assertEquals(actual.getBundleVersion(), version);

    } finally {
      if (driver != null) {
        driver.quit();
        driver = null;
      }
    }
  }


  @Test(dataProvider = "wrongCombination",expectedExceptions = WebDriverException.class)
  public void throwsOnInvalidSDKDeviceCombination(String sdk,DeviceVariation variation){
    IOSCapabilities cap = IOSCapabilities.iphone("UICatalog");
    cap.setSDKVersion(sdk);
    cap.setDeviceVariation(variation);
    driver = new RemoteIOSDriver(getRemoteURL(), cap);
  }


}
