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
import org.uiautomation.ios.utils.ClassicCommands;

public abstract class SafariLauncherTest extends BaseIOSDriverTest {


  @BeforeClass
  public abstract void checkSetup();

  @AfterMethod(alwaysRun = true)
  public void afterMethod() throws Exception {
    stopDriver();
  }

  // list of all the valid {sdk , version}
  @DataProvider
  public abstract Object[][] sdkAndSafariVersion();

  // list of  {expectedSDK , givenVersion}
  @DataProvider
  public abstract Object[][] versionToSDK();

  // list of {given sdk, given version}
  @DataProvider
  public abstract Object[][] invalid();

  @Test(dataProvider = "sdkAndSafariVersion",invocationCount = 1)
  public void supportAllInstalledSDKsSafari(String sdk, String version, DeviceVariation variation) throws InterruptedException {
    try {
      IOSCapabilities cap = IOSCapabilities.iphone("Safari", version);
      cap.setSDKVersion(sdk);

      cap.setDeviceVariation(variation);

      driver = new RemoteIOSDriver(getRemoteURL(), cap);
      IOSCapabilities actual = driver.getCapabilities();
      String url = "http://" + config.getHost() + ":" + config.getPort() + "/wd/hub/status";
      driver.get(url);

      Assert.assertEquals(actual.getSDKVersion(), sdk);
      Assert.assertEquals(actual.getBundleVersion(), version);
      String source = driver.getPageSource();
      Assert.assertTrue(source.contains("\"status\": 0"), source);

    } finally {
      if (driver != null) {
        driver.quit();
        driver = null;
      }
    }
  }


  @Test( dataProvider = "sdkAndSafariVersion", timeOut = 20000, dependsOnMethods = "supportAllInstalledSDKsSafari")
  public void safariGuessesVersionGivenASDKs(String sdk, String version, DeviceVariation variation) {
    try {
      IOSCapabilities cap = IOSCapabilities.iphone("Safari");
      cap.setDeviceVariation(variation);
      cap.setSDKVersion(sdk);
      driver = new RemoteIOSDriver(getRemoteURL(), cap);
      IOSCapabilities actual = driver.getCapabilities();
      String url = "http://" + config.getHost() + ":" + config.getPort() + "/wd/hub/status";
      driver.get(url);

      Assert.assertEquals(actual.getSDKVersion(), sdk);
      Assert.assertEquals(actual.getBundleVersion(), version);
      Assert.assertTrue(driver.getPageSource().contains("\"status\": 0"));

    } finally {
      if (driver != null) {
        driver.quit();
        driver = null;
      }
    }
  }


  @Test( dataProvider = "versionToSDK", timeOut = 20000, dependsOnMethods = "supportAllInstalledSDKsSafari")
  public void safariTakesLatestGivenAVersion(String sdk, String version) {
    try {
      IOSCapabilities cap = IOSCapabilities.iphone("Safari", version);

      driver = new RemoteIOSDriver(getRemoteURL(), cap);
      IOSCapabilities actual = driver.getCapabilities();
      String url = "http://" + config.getHost() + ":" + config.getPort() + "/wd/hub/status";
      driver.get(url);

      Assert.assertEquals(actual.getSDKVersion(), sdk);
      Assert.assertEquals(actual.getBundleVersion(), version);
      Assert.assertTrue(driver.getPageSource().contains("\"status\": 0"));

    } finally {
      if (driver != null) {
        driver.quit();
        driver = null;
      }
    }
  }


  @Test( dataProvider = "invalid", timeOut = 1000, expectedExceptions = WebDriverException.class)
  public void detectInvalidConfigs(String sdk, String version) {
    IOSCapabilities cap = IOSCapabilities.iphone("Safari", version);
    cap.setSDKVersion(sdk);
    driver = new RemoteIOSDriver(getRemoteURL(), cap);
    Assert.fail("shouldn't start with" + sdk + "::" + version);
  }
}
