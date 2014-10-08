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

package org.uiautomation.ios.utils;

import org.openqa.selenium.WebDriverException;
import org.uiautomation.ios.communication.device.DeviceVariation;
import org.uiautomation.ios.xcode.Xcode5Device;
import org.uiautomation.ios.xcode.Xcode5DeviceType;
import org.uiautomation.ios.xcode.Xcode5Runtime;
import org.uiautomation.ios.xcode.XcodeDevice;
import org.uiautomation.ios.xcode.XcodeDeviceType;
import org.uiautomation.ios.xcode.XcodeRuntime;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Xcode5DeviceMapping implements CommandOutputListener, DeviceMapping {

  private static final Pattern key = Pattern.compile("(.*) - Simulator - iOS (.*)");

  private final List<Xcode5Runtime> runtimes = new ArrayList<>();
  private final List<Xcode5DeviceType> deviceTypes = new ArrayList<>();
  private final List<Xcode5Device> devices = new ArrayList<>();

  @Override
  public void init() {
    List<String> listArgs = new ArrayList<>();
    listArgs.add("instruments");
    listArgs.add("-s");
    listArgs.add("devices");
    Command listCommand = new Command(listArgs, false);
    listCommand.registerListener(this);
    listCommand.executeAndWait(false, 5000);
  }

  @Override
  public XcodeDevice getDevice(XcodeRuntime rt, XcodeDeviceType type) {
    for (Xcode5Device d : devices) {
      if (d.getRuntime().equals(rt) && d.getDeviceType().equals(type)) {
        return d;
      }
    }
    throw new WebDriverException("Cannot find device with rt=" + rt + " and type = " + type);
  }

  @Override
  public XcodeRuntime getRuntime(String sdkVersion) {
    for (Xcode5Runtime rt : runtimes) {
      if (rt.getVersion().equals(sdkVersion)) {
        return rt;
      }
    }
    throw new WebDriverException("Cannot find runtime " + sdkVersion + " in " + runtimes);
  }


  @Override
  public XcodeDeviceType getDeviceType(DeviceVariation variation) {
    for (Xcode5DeviceType type : deviceTypes) {
      if (type.getDeviceVariation() == variation) {
        return type;
      }
    }
    throw new WebDriverException("cannot find variation " + variation);
  }

  @Override
  public void stdout(String log) {
    Matcher matcher = key.matcher(log);
    if (matcher.matches()) {
      String key = matcher.group(1);
      Xcode5DeviceType type = new Xcode5DeviceType(key);
      if (!deviceTypes.contains(type)) {
        deviceTypes.add(type);
      }
      String sdk = matcher.group(2);
      Xcode5Runtime rt = new Xcode5Runtime(sdk);
      if (!runtimes.contains(rt)) {
        runtimes.add(rt);
      }
      Xcode5Device d = new Xcode5Device(rt, type);
      if (d.isValid()) {
        devices.add(d);
      }
    }
  }

  @Override
  public void stderr(String log) {

  }
}
