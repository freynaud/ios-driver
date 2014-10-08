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


import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import org.uiautomation.ios.communication.device.DeviceVariation;

public class Xcode5DeviceType implements XcodeDeviceType {


  @Override
  public int hashCode() {
    return variation.hashCode();
  }

  private final String key;
  private final DeviceVariation variation;
  private static final BiMap<DeviceVariation, String> mapping = HashBiMap.create();

  static {
    mapping.put(DeviceVariation.iPhone4, "iPhone");
    mapping.put(DeviceVariation.iPhone4s, "iPhone Retina (3.5-inch)");
    mapping.put(DeviceVariation.iPhone5, "iPhone Retina (4-inch)");
    mapping.put(DeviceVariation.iPhone5s, "iPhone Retina (4-inch 64-bit)");
    mapping.put(DeviceVariation.iPad2, "iPad");
    mapping.put(DeviceVariation.iPadRetina, "iPad Retina");
    mapping.put(DeviceVariation.iPadAir, "iPad Retina (64-bit)");
  }

  public Xcode5DeviceType(String key) {
    this.key = key;
    this.variation = mapping.inverse().get(key);
    if (variation == null) {
      throw new RuntimeException("Cannot map " + key + " to a know variation.");
    }
  }

  public String getName() {
    return key;
  }


  public DeviceVariation getDeviceVariation() {
    return variation;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Xcode5DeviceType)) {
      return false;
    }

    Xcode5DeviceType that = (Xcode5DeviceType) o;

    if (variation != that.variation) {
      return false;
    }

    return true;
  }
}
