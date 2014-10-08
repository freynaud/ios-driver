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


public class Xcode5Runtime implements XcodeRuntime {

  private final String version;

  public Xcode5Runtime(String version) {
    this.version = version;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Xcode5Runtime that = (Xcode5Runtime) o;

    if (!version.equals(that.version)) {
      return false;
    }

    return true;
  }

  public String getVersion(){
    return version;
  }

  @Override
  public int hashCode() {
    return version.hashCode();
  }
}
