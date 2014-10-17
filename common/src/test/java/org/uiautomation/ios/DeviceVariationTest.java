package org.uiautomation.ios;

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.uiautomation.ios.communication.device.DeviceVariation.iPad2;
import static org.uiautomation.ios.communication.device.DeviceVariation.iPadAir;
import static org.uiautomation.ios.communication.device.DeviceVariation.iPadRetina;
import static org.uiautomation.ios.communication.device.DeviceVariation.iPhone4;
import static org.uiautomation.ios.communication.device.DeviceVariation.iPhone4s;
import static org.uiautomation.ios.communication.device.DeviceVariation.iPhone5;
import static org.uiautomation.ios.communication.device.DeviceVariation.iPhone5s;
import static org.uiautomation.ios.communication.device.DeviceVariation.iPhone6;
import static org.uiautomation.ios.communication.device.DeviceVariation.iPhone6Plus;


public class DeviceVariationTest {

  @Test
  public void canCheckCompatibilityMatrix() {
    assertTrue(iPhone4.canRunVersion("6"));
    assertFalse(iPhone4.canRunVersion("7"));
    assertFalse(iPhone4.canRunVersion("8"));

    assertTrue(iPhone4s.canRunVersion("6"));
    assertTrue(iPhone4s.canRunVersion("7"));
    assertTrue(iPhone4s.canRunVersion("8"));

    assertTrue(iPhone5.canRunVersion("6"));
    assertTrue(iPhone5.canRunVersion("7"));
    assertTrue(iPhone5.canRunVersion("8"));

    assertFalse(iPhone5s.canRunVersion("6"));
    assertTrue(iPhone5s.canRunVersion("7"));
    assertTrue(iPhone5s.canRunVersion("8"));

    assertFalse(iPhone6.canRunVersion("6"));
    assertFalse(iPhone6.canRunVersion("7"));
    assertTrue(iPhone6.canRunVersion("8"));

    assertFalse(iPhone6Plus.canRunVersion("6"));
    assertFalse(iPhone6Plus.canRunVersion("7"));
    assertTrue(iPhone6Plus.canRunVersion("8"));

    assertTrue(iPad2.canRunVersion("6"));
    assertTrue(iPad2.canRunVersion("7"));
    assertTrue(iPad2.canRunVersion("8"));

    assertTrue(iPadRetina.canRunVersion("6"));
    assertTrue(iPadRetina.canRunVersion("7"));
    assertTrue(iPadRetina.canRunVersion("8"));

    assertFalse(iPadAir.canRunVersion("6"));
    assertTrue(iPadAir.canRunVersion("7"));
    assertTrue(iPadAir.canRunVersion("8"));

  }


}
