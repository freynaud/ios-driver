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
import org.uiautomation.ios.utils.IOSVersion;

public enum DeviceVariation {


  // ios8 format
  ResizableiPad,
  ResizableiPhone,

  iPad2,
  iPadRetina,
  iPadAir,

  iPhone4,
  iPhone4s,
  iPhone5,
  iPhone5s,
  iPhone6Plus,
  iPhone6;


  
  public static boolean is64bit(DeviceVariation variation) {
    if (variation == null)
      return false;
    return variation.toString().endsWith("_64bit");
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
    throw new WebDriverException("Cannot cast " + (o == null ? "null" : o.getClass()) + " to IOSDevice");
  }
}


