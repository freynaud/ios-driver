/*
 * Copyright 2012 ios-driver committers.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License
 *  is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  or implied. See the License for the specific language governing permissions and limitations under
 *  the License.
 */

package org.uiautomation.ios.server;

import org.libimobiledevice.binding.raw.ApplicationInfo;
import org.libimobiledevice.binding.raw.DeviceInfo;
import org.libimobiledevice.binding.raw.IMobileDeviceFactory;
import org.uiautomation.ios.IOSCapabilities;
import org.uiautomation.ios.communication.device.DeviceType;
import org.uiautomation.ios.communication.device.DeviceVariation;
import org.uiautomation.ios.server.application.APPIOSApplication;
import org.uiautomation.ios.server.application.IPAApplication;


import org.uiautomation.iosdriver.services.DeviceInstallerService;

import java.util.List;

public class RealDevice extends Device {

  private final String uuid;
  private final DeviceType type;
  private final String name;
  private final String buildVersion;
  private final String productType;
  private final String iosVersion;
  private IMobileDeviceFactory factory = IMobileDeviceFactory.INSTANCE;

  public RealDevice(DeviceInfo info) {
    this.uuid = info.getUniqueDeviceID();
    this.name = info.getDeviceName();
    this.type = DeviceType.valueOf(info.getDeviceClass().toLowerCase());
    this.buildVersion = info.getBuildVersion();
    this.productType = info.getProductType();
    this.iosVersion = info.getProductVersion();
  }


  public String getUuid() {
    return uuid;
  }

  public DeviceType getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public String getIosVersion() {
    return iosVersion;
  }

  public String getProductType() {
    return productType;
  }

  public String getBuildVersion() {
    return buildVersion;
  }

  public List<ApplicationInfo> getApplications() {
    String xml =factory.get(uuid).listApplication("list_user");
    return ApplicationInfo.extractApplications(xml);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    RealDevice that = (RealDevice) o;

    if (!uuid.equals(that.uuid)) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return uuid.hashCode();
  }

  @Override
  public IOSCapabilities getCapability() {
    IOSCapabilities res = new IOSCapabilities();
    res.setCapability(IOSCapabilities.SIMULATOR, false);
    res.setCapability(IOSCapabilities.UI_SDK_VERSION, iosVersion);
    res.setCapability(IOSCapabilities.DEVICE, type);
    //res.setCapability(IOSCapabilities.VARIATION, DeviceVariation.valueOf(this.productType.replace(",", "")));
    res.setCapability(IOSCapabilities.UUID, uuid);
    return res;
  }

  @Override
  public boolean canRun(APPIOSApplication app) {
    if (!(app instanceof IPAApplication)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString(){
    return this.uuid+", name:"+this.name;
  }
}

