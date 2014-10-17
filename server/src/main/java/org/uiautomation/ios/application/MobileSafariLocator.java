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

package org.uiautomation.ios.application;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriverException;
import org.uiautomation.ios.IOSServer;
import org.uiautomation.ios.utils.ClassicCommands;
import org.uiautomation.ios.utils.IOSVersion;
import org.uiautomation.ios.utils.PlistFileUtils;
import org.uiautomation.ios.utils.SDKVersion;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class MobileSafariLocator {

  private static final Logger log = Logger.getLogger(MobileSafariLocator.class.getName());
  private static File xcode = ClassicCommands.getXCodeInstall();
  private static final Map<String, APPIOSApplication> safariCopies = new HashMap<>();

  public static APPIOSApplication locateSafari(String xcodeVersion,String sdkVersion) {
    APPIOSApplication res = safariCopies.get(xcodeVersion+sdkVersion);
    if (res !=null){
      return res;
    }
   throw new WebDriverException("Cannot find safari for " + sdkVersion + " in the known mobile safari list.");
  }


  public static APPIOSApplication locateSafariInstall(String xcodeVersion,String sdkVersion) {
    if (new IOSVersion(sdkVersion).isGreaterOrEqualTo("8.0")){
      File app = APPIOSApplication.findSafariLocation(xcode, sdkVersion);
      return new APPIOSApplication(app.getAbsolutePath());
    }else{
      APPIOSApplication res = safariCopies.get(xcodeVersion+sdkVersion);
      if (res == null) {
        res = copyOfSafari(xcode, xcodeVersion,sdkVersion);
        safariCopies.put(xcodeVersion+sdkVersion, res);
      }
      return res;
    }
  }

  public static File getSafariCopy(String xcodeVersion,String sdk){
    File parent = new File(IOSServer.getTmpIOSFolder().getAbsolutePath(), "safariCopies/"+xcodeVersion);
    File copy = new File(parent,"safari-" + sdk + ".app");
    return copy;
  }
  private static APPIOSApplication copyOfSafari(File xcodeInstall,String xcodeVersion, String sdk) {
    File parent = new File(IOSServer.getTmpIOSFolder().getAbsolutePath(), "safariCopies/"+xcodeVersion);
    parent.mkdirs();
    File copy = new File(parent,"safari-" + sdk + ".app");
    if (!copy.exists()) {
      File safariFolder = APPIOSApplication.findSafariLocation(xcodeInstall, sdk);
      copy.mkdirs();
      try {
        FileUtils.copyDirectory(safariFolder, copy);
        setSafariBuiltinFavorites(copy);
      } catch (IOException e) {
        log.warning("Cannot create the copy of safari : " + e.getMessage());
      }
    }
    return new APPIOSApplication(copy.getAbsolutePath());
  }

  public static void setSafariBuiltinFavorites(File safari) {
    System.out.println("REMOVING SPLASH");
    File[] files = safari.listFiles(new FilenameFilter() {
      @Override
      public boolean accept(File dir, String name) {
        return name.startsWith("BuiltinFavorites") && name.endsWith(".plist");
      }
    });
    for (File plist : files) {
      setSafariBuiltinFavories(plist);
    }
  }

  private static void setSafariBuiltinFavories(File builtinFavoritesPList) {
    try {
      PlistFileUtils.PListFormat format = PlistFileUtils.getFormat(builtinFavoritesPList);

      NSArray root = new NSArray(1);
      NSDictionary favorite = new NSDictionary();
      favorite.put("Title", "about:blank");
      favorite.put("URL", "about:blank");
      root.setValue(0, favorite);

      PlistFileUtils.write(builtinFavoritesPList, root, format);
    } catch (Exception e) {
      throw new WebDriverException("Cannot set " + builtinFavoritesPList.getAbsolutePath()
                                   + ": " + e.getMessage(), e);
    }
  }
}
