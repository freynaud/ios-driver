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

package org.uiautomation.ios.communication.device;

import org.openqa.selenium.WebDriverException;

import java.util.Arrays;
import java.util.List;

public enum DeviceVariation {

  // ios8 format
//  ResizableiPad,
//  ResizableiPhone,

  iPad2,
  iPadRetina,
  iPadAir,

  iPhone4,
  iPhone4s,
  iPhone5,
  iPhone5s,
  iPhone6Plus,
  iPhone6;


  public boolean is64bit() {

    switch (this) {
      case iPad2:
      case iPadAir:
        return false;
      case iPadRetina:
        return true;
      case iPhone4:
      case iPhone4s:
      case iPhone5:
        return false;
      case iPhone5s:
      case iPhone6Plus:
      case iPhone6:
        return true;
      default:
        throw new WebDriverException(this + " not implemented.");
    }
  }

  public static DeviceVariation valueOf(Object o) {
    if (o instanceof DeviceVariation) {
      return (DeviceVariation) o;
    } else if (o instanceof String) {
      for (DeviceVariation variation : DeviceVariation.values()) {
        if (variation.toString().equals(o)) {
          return variation;
        }
      }
      throw new WebDriverException("not a valid DeviceVariation string: " + o);
    }
    throw new WebDriverException(
        "Cannot cast " + (o == null ? "null" : o.getClass()) + " to IOSDevice");
  }


  public boolean canRunVersion(String majorIOSVersion) {
    return getSupportedIOSVersion().contains(majorIOSVersion);
  }


  private List<String> getSupportedIOSVersion() {
    switch (this) {
      case iPhone4:
        return Arrays.asList(new String[]{"6"});
      case iPhone4s:
      case iPhone5:
        return Arrays.asList(new String[]{"6", "7", "8"});
      case iPhone5s:
        return Arrays.asList(new String[]{"7", "8"});
      case iPhone6:
      case iPhone6Plus:
        return Arrays.asList(new String[]{"8"});
      case iPad2:
      case iPadRetina:
        return Arrays.asList(new String[]{"6", "7", "8"});
      case iPadAir:
        return Arrays.asList(new String[]{"7", "8"});
      default:
        throw new WebDriverException("Not implemented " + this);
    }
  }
}


