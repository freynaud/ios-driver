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


public class DeviceMappingFactory {


  public static DeviceMapping create() {
    IOSVersion v = ClassicCommands.getXCodeVersion();
    String major = v.getMajor();
    switch (major) {
      case "5":
        return new Xcode5DeviceMapping();
      case "6":
        return new XCode6DeviceMapping();
      default:
        throw new RuntimeException("xcode version not implemented :" + v);
    }
  }
}
