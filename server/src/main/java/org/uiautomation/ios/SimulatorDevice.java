/*
 * Copyright 2012-2013 eBay Software Foundation and ios-driver committers
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

package org.uiautomation.ios;

import org.uiautomation.ios.application.APPIOSApplication;
import org.uiautomation.ios.application.IPAApplication;
import org.uiautomation.ios.communication.device.DeviceType;
import org.uiautomation.ios.communication.device.DeviceVariation;
import org.uiautomation.ios.instruments.InstrumentsVersion;
import org.uiautomation.ios.utils.ClassicCommands;
import org.uiautomation.ios.utils.IOSVersion;
import org.uiautomation.ios.xcode.XcodeDeviceType;
import org.uiautomation.ios.xcode.XcodeRuntime;

import java.util.List;

public class SimulatorDevice extends Device {

  private final HostInfo info;

  private SimulatorDevice() {
    info = null;
  }

  public SimulatorDevice(HostInfo hostInfo) {
    this.info = hostInfo;
  }

  @Override
  public IOSCapabilities getCapability() {
    IOSCapabilities res = new IOSCapabilities();
    res.setCapability(IOSCapabilities.SIMULATOR, true);
    res.setCapability(IOSCapabilities.UI_SDK_VERSION, ClassicCommands.getDefaultSDK());
    res.setCapability(IOSCapabilities.UI_SDK_VERSION_ALT, getAltSDK());
    res.setCapability(IOSCapabilities.DEVICE, DeviceType.iphone);
    res.setCapability(IOSCapabilities.DEVICE + "_Alt",
                      new DeviceType[]{DeviceType.iphone, DeviceType.ipad});
    return res;
  }

  private List<String> getAltSDK() {
    List<String> supported = info.getInstalledSDKs();
    return supported;
  }

  @Override
  public boolean canRun(APPIOSApplication app) {
    if (app instanceof IPAApplication) {
      return false;
    }

    return true;
  }

  @Override
  public String toString() {
    return "Simulator[(sdks=" + getAltSDK() + ")" + (isBusy() ? "in use by ios-driver" : "available") + ']';
  }


  public RunningSimulatorDevice getSpecificDevice(DeviceType device, DeviceVariation variation, String sdk, InstrumentsVersion v) {
    return new RunningSimulatorDevice(this, device, variation, sdk, v);
  }


  public static class RunningSimulatorDevice extends SimulatorDevice {

    private final DeviceType device;
    private final DeviceVariation variation;
    private final String sdk;
    private final InstrumentsVersion v;
    private final SimulatorDevice sim;

    public RunningSimulatorDevice(SimulatorDevice sim, DeviceType device, DeviceVariation variation, String sdk, InstrumentsVersion v) {
      this.sim = sim;
      this.device = device;
      this.variation = variation;
      this.sdk = sdk;
      this.v = v;
    }

    public String getKey() {
      XcodeRuntime rt = sim.info.getDeviceUUIDMap().getRuntime(sdk);
      XcodeDeviceType t = sim.info.getDeviceUUIDMap().getDeviceType(variation);
      return sim.info.getDeviceUUIDMap().getDevice(rt, t).getInstrumentsWDevice();
    }

  }
}
