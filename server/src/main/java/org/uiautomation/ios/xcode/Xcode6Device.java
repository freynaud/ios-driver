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

package org.uiautomation.ios.xcode;


public class Xcode6Device implements XcodeDevice {

  private final String shortName;
  private final String name;
  private final String uuid;
  private final Xcode6Runtime runtime;
  private final Xcode6DeviceType type;
  private String instrumentsWDevice;

  public Xcode6Device(Xcode6DeviceType type, String uuid, Xcode6Runtime runtime) {
    this.type = type;
    this.name = type.getName();
    this.shortName = type.getShortName();
    this.uuid = uuid;
    this.runtime = runtime;
  }

  public Xcode6Runtime getRuntime() {
    return runtime;
  }

  public String getName() {
    return name;
  }

  public String getShortName() {
    return shortName;
  }

  public String getUuid() {
    return uuid;
  }




  public Xcode6DeviceType getDeviceType() {
    return type;
  }

  public void setInstrumentsWDevice(String instrumentsWDevice){
    this.instrumentsWDevice = instrumentsWDevice;
  }
  @Override
  public String getInstrumentsWDevice() {
    return instrumentsWDevice;
  }
}
