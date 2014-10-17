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
package org.uiautomation.ios.utils;

import com.dd.plist.BinaryPropertyListWriter;
import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.NSObject;
import com.dd.plist.PropertyListParser;

import org.json.JSONObject;
import org.libimobiledevice.ios.driver.binding.model.ApplicationInfo;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class PlistFileUtils {

  private final File source;
  private final Map<String, Object> plistContent;

  public PlistFileUtils(File source) {
    this.source = source;
    this.plistContent = read(source);
  }

  public enum PListFormat {
    binary, text, xml
  }

  private Map<String, Object> read(File plist) {
    try {
      NSObject object = PropertyListParser.parse(plist);
      ApplicationInfo info = new ApplicationInfo(object);
      return info.getProperties();
    } catch (Exception ex) {
      throw new WebDriverException(
          String.format("In %s: Cannot parse %s: %s", source, plist.getAbsolutePath(), ex.getMessage()),
          ex);
    }
  }
  public static void write(File dest, NSObject content, PlistFileUtils.PListFormat format) throws IOException {
    switch (format) {
      case binary:
        BinaryPropertyListWriter.write(dest, content);
        break;
      case xml:
        PropertyListParser.saveAsXML(content, dest);
        break;
      case text:
        if (content instanceof NSDictionary) {
          PropertyListParser.saveAsASCII((NSDictionary) content, dest);
        } else if (content instanceof NSArray) {
          PropertyListParser.saveAsASCII((NSArray) content, dest);
        } else {
          throw new IllegalArgumentException("Invalid content type for ascii: " + content.getClass());
        }
        break;
      default:
        throw new IllegalArgumentException("Invalid plist output format: " + format);
    }
  }

  public static PListFormat getFormat(File f) throws IOException {
    FileInputStream fis = new FileInputStream(f);
    byte b[] = new byte[8];
    fis.read(b, 0, 8);
    String magicString = new String(b);
    fis.close();
    if (magicString.startsWith("bplist")) {
      return PListFormat.binary;
    } else if (magicString.trim().startsWith("(") || magicString.trim().startsWith("{")
               || magicString.trim().startsWith("/")) {
      return PListFormat.text;
    } else {
      return PListFormat.xml;
    }
  }

  public JSONObject toJSON() throws Exception {
    return new JSONObject(plistContent);
  }
}