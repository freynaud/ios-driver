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


public class Xcode6Runtime implements XcodeRuntime {

  private final String shortVersion;
  private final String version;


  private final String build;
  private final String details;


  public Xcode6Runtime(String shortVersion, String version, String build, String details) {
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
