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

import org.uiautomation.ios.utils.Command;

import java.util.Arrays;

/**
 * Created by freynaud on 19/09/2014.
 */
public class Xcode601 implements Xcode {


  @Override
  public void openURL(String uuid,String url) {
    String[] args = new String[]{"xcrun", "simctl", "openurl", "51CB648A-25C7-4E16-AAB5-0FDBEBF4E700", url};
    Command c = new Command(Arrays.asList(args), false);
    c.executeAndWait(true,5000);
  }


  public static void loadSimulatorInfo() {

  }


  public static class DeviceType {


    private final String shortName;
    private final String name;

    public DeviceType(String shortName, String name) {
      this.shortName = shortName;
      this.name = name;
    }

    public String getShortName() {
      return shortName;
    }

    public String getName() {
      return name;
    }
  }

  public static class Runtime {

    private final String shortVersion;
    private final String version;


    private final String build;
    private final String details;


    public Runtime(String shortVersion, String version, String build, String details) {
      this.shortVersion = shortVersion;
      this.version = version;
      this.build = build;
      this.details = details;
    }

    public String getShortVersion() {
      return shortVersion;
    }

    public String getVersion() {
      return version;
    }

    public String getBuild() {
      return build;
    }

    public String getDetails() {
      return details;
    }
  }

  public static class Device {


    private final String shortName;
    private final String name;
    private final String uuid;
    private final Runtime runtime;
    private final DeviceType type;
    private String key;

    public Device(DeviceType type, String uuid, Runtime runtime) {
      this.type = type;
      this.name = type.getName();
      this.shortName = type.getShortName();
      this.uuid = uuid;
      this.runtime = runtime;
    }

    public Runtime getRuntime() {
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

    public void setKey(String key) {
      this.key = key;
    }

    public String getKey() {
      return key;
    }

    public DeviceType getDeviceType() {
      return type;
    }
  }


}
