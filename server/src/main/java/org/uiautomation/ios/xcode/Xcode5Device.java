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


public class Xcode5Device implements XcodeDevice {


  private final Xcode5Runtime rt;
  private final Xcode5DeviceType type;

  public Xcode5Runtime getRuntime() {
    return rt;
  }

  public Xcode5DeviceType getDeviceType() {
    return type;
  }

  public Xcode5Device(Xcode5Runtime rt, Xcode5DeviceType type) {
    this.rt = rt;
    this.type = type;
  }

  public boolean isValid() {
    return true;
  }

  @Override
  public String getInstrumentsWDevice() {
    return type.getName() + " - Simulator - iOS " + rt.getVersion();
  }
}
