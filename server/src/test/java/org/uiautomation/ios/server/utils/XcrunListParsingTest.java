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

package org.uiautomation.ios.server.utils;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.uiautomation.ios.communication.device.DeviceVariation;
import org.uiautomation.ios.utils.XCode6DeviceMapping;
import org.uiautomation.ios.xcode.Xcode6Device;
import org.uiautomation.ios.xcode.Xcode6DeviceType;
import org.uiautomation.ios.xcode.Xcode6Runtime;


public class XcrunListParsingTest {

  private XCode6DeviceMapping parser = new XCode6DeviceMapping();

  @BeforeClass
  public void setup() {
    for (String s : output.split("\n")) {
      parser.stdout(s);
    }

    for (String s : deviceoutput.split("\n")) {
      parser.stdout(s);
    }

  }

  @Test
  public void getDeviceTypes() {
    Assert.assertEquals(parser.getDeviceTypes().size(), 10);
  }

  @Test
  public void getRuntimes() {
    Assert.assertEquals(parser.getRuntimes().size(), 3);
  }

  @Test
  public void getDevices() {
    Assert.assertEquals(parser.getDevices().size(), 22);
  }

  @Test
  public void parsesTypeOk() {
    Xcode6DeviceType type = parser.getDeviceTypes().get(0);
    Assert.assertEquals(type.getShortName(), "iPhone 4s");
    Assert.assertEquals(type.getName(), "com.apple.CoreSimulator.SimDeviceType.iPhone-4s");
  }

  @Test
  public void parsesRuntimeOk() {
    Xcode6Runtime rt = parser.getRuntimes().get(1);
    Assert.assertEquals(rt.getShortVersion(), "7.1");
    Assert.assertEquals(rt.getVersion(), "7.1");
    Assert.assertEquals(rt.getBuild(), "11D167");
    Assert.assertEquals(rt.getDetails(), "com.apple.CoreSimulator.SimRuntime.iOS-7-1");
  }

  @Test
  public void parsesRuntimeOkWithLongVersion() {
    Xcode6Runtime rt = parser.getRuntimes().get(0);
    Assert.assertEquals(rt.getShortVersion(), "7.0");
    Assert.assertEquals(rt.getVersion(), "7.0.3");
    Assert.assertEquals(rt.getBuild(), "11B507");
    Assert.assertEquals(rt.getDetails(), "com.apple.CoreSimulator.SimRuntime.iOS-7-0");
  }

  @Test
  public void parsesDevicesOk() {
    Xcode6Device d = parser.getDevices().get(0);
    Assert.assertEquals(d.getRuntime(), parser.getRuntimes().get(0));
    Assert.assertEquals(d.getUuid(), "C1D891BF-A906-4E34-B467-C3F4138A91F1");
    Assert.assertEquals(d.getShortName(), "iPhone 4s");
    Assert.assertEquals(d.getName(), "com.apple.CoreSimulator.SimDeviceType.iPhone-4s");
    Assert.assertEquals(d.getInstrumentsWDevice(), "iPhone 4s (7.0.3 Simulator)");
  }

  @Test
  public void getDevice() {
    Xcode6Runtime rt703 = parser.getRuntime("7.0.3");
    Xcode6DeviceType type = (Xcode6DeviceType) parser.getDeviceType(DeviceVariation.iPhone4s);
    Xcode6Device d = (Xcode6Device) parser.getDevice(rt703, type);
    Assert.assertEquals(d.getUuid(), "C1D891BF-A906-4E34-B467-C3F4138A91F1");
    Assert.assertEquals(d.getInstrumentsWDevice(), "iPhone 4s (7.0.3 Simulator)");
  }

  @Test
  public void getDevice2() {
    Xcode6Runtime rt8 = parser.getRuntime("8.0");
    Xcode6DeviceType type = (Xcode6DeviceType) parser.getDeviceType(DeviceVariation.iPhone6Plus);
    Xcode6Device d = (Xcode6Device) parser.getDevice(rt8, type);
    Assert.assertEquals(d.getUuid(), "0817B996-B255-4662-B495-6E345A0FE7FF");
    Assert.assertEquals(d.getInstrumentsWDevice(), "iPhone 6 Plus (8.0 Simulator)");
  }

  @Test
  public void getDevice3() {
    Xcode6Runtime rt8 = parser.getRuntime("8.0");
    Xcode6DeviceType type = (Xcode6DeviceType) parser.getDeviceType(DeviceVariation.iPadRetina);
    Xcode6Device d = (Xcode6Device) parser.getDevice(rt8, type);
    Assert.assertEquals(d.getUuid(), "C4805133-84D7-4283-88A2-4329D8728BBB");
    Assert.assertEquals(d.getInstrumentsWDevice(), "iPad Retina (8.0 Simulator)");
  }

  private String deviceoutput = "Known Devices:\n"
                                + "freynaud-mbp [023290A0-07D8-5FF7-9E74-E266D9FFE4B0]\n"
                                + "Resizable iPad (8.0 Simulator) [6F5DA88B-A160-41AD-83A1-862C0CBCDFF2]\n"
                                + "Resizable iPhone (8.0 Simulator) [8FBFD51B-592A-4661-948F-A1EF9A6B65D6]\n"
                                + "iPad 2 (7.0.3 Simulator) [D1C131CC-44EC-4F2A-A42E-8EC2B2FF6E36]\n"
                                + "iPad 2 (7.1 Simulator) [655F7AD7-8D02-458C-9799-0CADE8DFD531]\n"
                                + "iPad 2 (8.0 Simulator) [5D6766C2-9526-4EF3-BC67-D55ABF48B8A2]\n"
                                + "iPad Air (7.0.3 Simulator) [2B743844-0DF2-4BEA-B037-033809C0025C]\n"
                                + "iPad Air (7.1 Simulator) [8CFC72CD-45C3-483B-B516-52971BF2D671]\n"
                                + "iPad Air (8.0 Simulator) [C999B5FC-0C1C-4ED0-B172-06B736372681]\n"
                                + "iPad Retina (7.0.3 Simulator) [B900B794-14CF-44BA-84F9-30208E44E56C]\n"
                                + "iPad Retina (7.1 Simulator) [9D837666-4CFB-4A54-9A82-ACE10318D19E]\n"
                                + "iPad Retina (8.0 Simulator) [C4805133-84D7-4283-88A2-4329D8728BBB]\n"
                                + "iPhone 4s (7.0.3 Simulator) [C1D891BF-A906-4E34-B467-C3F4138A91F1]\n"
                                + "iPhone 4s (7.1 Simulator) [C6514AEA-3832-432B-9556-C672AF5DB591]\n"
                                + "iPhone 4s (8.0 Simulator) [8735A086-83D7-4098-92E2-7FCDCE69FA02]\n"
                                + "iPhone 5 (7.0.3 Simulator) [C2B1E530-6189-4145-87D0-7D6A381CDB44]\n"
                                + "iPhone 5 (7.1 Simulator) [19453CF3-4926-4422-A8EE-1FC1BE44C35A]\n"
                                + "iPhone 5 (8.0 Simulator) [96A4D2FC-7792-4FFF-BBEF-EE193F647A32]\n"
                                + "iPhone 5s (7.0.3 Simulator) [8B826F3D-7E9C-4D52-A5CD-68461B131BFA]\n"
                                + "iPhone 5s (7.1 Simulator) [4582A6D7-4E1B-4AAB-AD4E-6023EA4221D8]\n"
                                + "iPhone 5s (8.0 Simulator) [081D8CD6-1825-4F08-A06E-D7042992E282]\n"
                                + "iPhone 6 (8.0 Simulator) [51CB648A-25C7-4E16-AAB5-0FDBEBF4E700]\n"
                                + "iPhone 6 Plus (8.0 Simulator) [0817B996-B255-4662-B495-6E345A0FE7FF]";

  private String output = "== Device Types ==\n"
                          + "iPhone 4s (com.apple.CoreSimulator.SimDeviceType.iPhone-4s)\n"
                          + "iPhone 5 (com.apple.CoreSimulator.SimDeviceType.iPhone-5)\n"
                          + "iPhone 5s (com.apple.CoreSimulator.SimDeviceType.iPhone-5s)\n"
                          + "iPhone 6 Plus (com.apple.CoreSimulator.SimDeviceType.iPhone-6-Plus)\n"
                          + "iPhone 6 (com.apple.CoreSimulator.SimDeviceType.iPhone-6)\n"
                          + "iPad 2 (com.apple.CoreSimulator.SimDeviceType.iPad-2)\n"
                          + "iPad Retina (com.apple.CoreSimulator.SimDeviceType.iPad-Retina)\n"
                          + "iPad Air (com.apple.CoreSimulator.SimDeviceType.iPad-Air)\n"
                          + "Resizable iPhone (com.apple.CoreSimulator.SimDeviceType.Resizable-iPhone)\n"
                          + "Resizable iPad (com.apple.CoreSimulator.SimDeviceType.Resizable-iPad)\n"
                          + "== Runtimes ==\n"
                          + "iOS 7.0 (7.0.3 - 11B507) (com.apple.CoreSimulator.SimRuntime.iOS-7-0)\n"
                          + "iOS 7.1 (7.1 - 11D167) (com.apple.CoreSimulator.SimRuntime.iOS-7-1)\n"
                          + "iOS 8.0 (8.0 - 12A365) (com.apple.CoreSimulator.SimRuntime.iOS-8-0)\n"
                          + "== Devices ==\n"
                          + "-- iOS 7.0 --\n"
                          + "    iPhone 4s (C1D891BF-A906-4E34-B467-C3F4138A91F1) (Shutdown)\n"
                          + "    iPhone 5 (C2B1E530-6189-4145-87D0-7D6A381CDB44) (Shutdown)\n"
                          + "    iPhone 5s (8B826F3D-7E9C-4D52-A5CD-68461B131BFA) (Shutdown)\n"
                          + "    iPad 2 (D1C131CC-44EC-4F2A-A42E-8EC2B2FF6E36) (Shutdown)\n"
                          + "    iPad Retina (B900B794-14CF-44BA-84F9-30208E44E56C) (Shutdown)\n"
                          + "    iPad Air (2B743844-0DF2-4BEA-B037-033809C0025C) (Shutdown)\n"
                          + "-- iOS 7.1 --\n"
                          + "    iPhone 4s (C6514AEA-3832-432B-9556-C672AF5DB591) (Shutdown)\n"
                          + "    iPhone 5 (19453CF3-4926-4422-A8EE-1FC1BE44C35A) (Shutdown)\n"
                          + "    iPhone 5s (4582A6D7-4E1B-4AAB-AD4E-6023EA4221D8) (Shutdown)\n"
                          + "    iPad 2 (655F7AD7-8D02-458C-9799-0CADE8DFD531) (Shutdown)\n"
                          + "    iPad Retina (9D837666-4CFB-4A54-9A82-ACE10318D19E) (Shutdown)\n"
                          + "    iPad Air (8CFC72CD-45C3-483B-B516-52971BF2D671) (Shutdown)\n"
                          + "-- iOS 8.0 --\n"
                          + "    iPhone 4s (8735A086-83D7-4098-92E2-7FCDCE69FA02) (Shutdown)\n"
                          + "    iPhone 5 (96A4D2FC-7792-4FFF-BBEF-EE193F647A32) (Shutdown)\n"
                          + "    iPhone 5s (081D8CD6-1825-4F08-A06E-D7042992E282) (Shutdown)\n"
                          + "    iPhone 6 Plus (0817B996-B255-4662-B495-6E345A0FE7FF) (Shutdown)\n"
                          + "    iPhone 6 (51CB648A-25C7-4E16-AAB5-0FDBEBF4E700) (Shutdown)\n"
                          + "    iPad 2 (5D6766C2-9526-4EF3-BC67-D55ABF48B8A2) (Shutdown)\n"
                          + "    iPad Retina (C4805133-84D7-4283-88A2-4329D8728BBB) (Shutdown)\n"
                          + "    iPad Air (C999B5FC-0C1C-4ED0-B172-06B736372681) (Shutdown)\n"
                          + "    Resizable iPhone (8FBFD51B-592A-4661-948F-A1EF9A6B65D6) (Shutdown)\n"
                          + "    Resizable iPad (6F5DA88B-A160-41AD-83A1-862C0CBCDFF2) (Shutdown)";

}
