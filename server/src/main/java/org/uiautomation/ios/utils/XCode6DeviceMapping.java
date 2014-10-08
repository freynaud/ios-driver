package org.uiautomation.ios.utils;

import org.openqa.selenium.WebDriverException;
import org.uiautomation.ios.communication.device.DeviceVariation;
import org.uiautomation.ios.xcode.Xcode6Device;
import org.uiautomation.ios.xcode.Xcode6DeviceType;
import org.uiautomation.ios.xcode.Xcode6Runtime;
import org.uiautomation.ios.xcode.XcodeDevice;
import org.uiautomation.ios.xcode.XcodeDeviceType;
import org.uiautomation.ios.xcode.XcodeRuntime;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XCode6DeviceMapping implements CommandOutputListener, DeviceMapping {

  private static final Pattern sectionPattern = Pattern.compile("== (.*) ==|Known Devices:");
  private static final Pattern deviceTypePattern = Pattern.compile("(.*) \\(com.apple.CoreSimulator.SimDeviceType.(.*)\\)");
  private static final Pattern runtimePattern = Pattern.compile("iOS (.*) \\((.*) - (.*)\\) \\(com.apple.CoreSimulator.SimRuntime.(.*)\\)");
  private static final Pattern sdkPattern = Pattern.compile("-- iOS (.*) --");
  private static final Pattern simulatorPattern = Pattern.compile("    (.*) \\((\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12})\\) \\(\\w*\\)");
  private static final Pattern devicePattern = Pattern.compile("(.*) \\((.*) Simulator\\) \\[(\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12})\\]");


  private String currentSection;
  private Xcode6Runtime currentSDK;

  private final List<Xcode6Runtime> runtimes = new ArrayList<>();
  private final List<Xcode6DeviceType> deviceTypes = new ArrayList<>();
  private final List<Xcode6Device> devices = new ArrayList<>();
  private Map<String, String> simulatorToUUID = new HashMap<>();


  @Override
  public void init() {
    List<String> listArgs = new ArrayList<>();
    listArgs.add("xcrun");
    listArgs.add("simctl");
    listArgs.add("list");
    Command listCommand = new Command(listArgs, false);
    listCommand.registerListener(this);
    listCommand.executeAndWait(false, 5000);

    List<String> c = new ArrayList<>();
    c.add("instruments");
    c.add("-s");
    c.add("devices");
    Command cmd = new Command(c, false);
    cmd.registerListener(this);
    cmd.executeAndWait();
  }


  @Override
  public void stdout(String log) {

    Matcher sectionMatcher = sectionPattern.matcher(log);
    if (sectionMatcher.matches()) {
      currentSection = (sectionMatcher.group(1) == null) ? sectionMatcher.group(0) : sectionMatcher.group(1);
      return;
    }

    switch (currentSection) {
      case "Device Types":
        Matcher typeMatcher = deviceTypePattern.matcher(log);
        if (typeMatcher.matches()) {
          String shortName = typeMatcher.group(1);
          String suffix = typeMatcher.group(2);
          deviceTypes.add(new Xcode6DeviceType(shortName, "com.apple.CoreSimulator.SimDeviceType." + suffix));
        }
        break;
      case "Runtimes":
        Matcher rtMatcher = runtimePattern.matcher(log);
        if (rtMatcher.matches()) {
          String shortVersion = rtMatcher.group(1);
          String version = rtMatcher.group(2);
          String build = rtMatcher.group(3);
          String details = "com.apple.CoreSimulator.SimRuntime." + rtMatcher.group(4);
          runtimes.add(new Xcode6Runtime(shortVersion, version, build, details));
        }
        break;
      case "Devices":
        Matcher sdkMatcher = sdkPattern.matcher(log);
        if (sdkMatcher.matches()) {
          String shortVersion = sdkMatcher.group(1);
          currentSDK = getRuntimeFromShortVersion(shortVersion);
        }

        Matcher simulatorMatcher = simulatorPattern.matcher(log);
        if (simulatorMatcher.matches()) {
          String name = simulatorMatcher.group(1);
          String uuid = simulatorMatcher.group(2);
          Xcode6DeviceType type = getTypeFromName(name);
          devices.add(new Xcode6Device(type, uuid, currentSDK));
        }
        break;
      case "Known Devices:":
        Matcher dev = devicePattern.matcher(log);
        if (dev.matches()) {
          //iPad Retina (7.0.3 Simulator)
          String name = dev.group(1);
          String sdk = dev.group(2);
          String uuid = dev.group(3);
          String key = String.format("%s (%s Simulator)", name, sdk);
          setName(uuid, key);
        }
        break;
      default:
        throw new RuntimeException("Can't parse the simulator list from xcrun.--" + currentSection + "--");
    }
  }

  private Xcode6DeviceType getTypeFromName(String shortName) {
    for (Xcode6DeviceType type : deviceTypes) {
      if (type.getShortName().equals(shortName)) {
        return type;
      }
    }
    throw new WebDriverException("Cannot find device type with name " + shortName);
  }

  private void setName(String uuid, String key) {
    for (Xcode6Device device : devices) {
      if (device.getUuid().equals(uuid)) {
        device.setInstrumentsWDevice(key);
        return;
      }
    }
    throw new WebDriverException("unknown device" + uuid);
  }

  @Override
  public void stderr(String log) {
  }

  private Xcode6Runtime getRuntimeFromShortVersion(String shortVersion) {
    for (Xcode6Runtime r : runtimes) {
      if (r.getShortVersion().equals(shortVersion)) {
        return r;
      }
    }
    throw new InvalidParameterException("Cannot find " + shortVersion + " in the installed SDKs. Installed=" + runtimes);
  }

  public List<Xcode6DeviceType> getDeviceTypes() {
    return deviceTypes;
  }

  public String getUUID(String sdkVersion, String deviceName) {
    return simulatorToUUID.get(sdkVersion + "," + deviceName);
  }


  public List<Xcode6Runtime> getRuntimes() {
    return runtimes;
  }

  public List<Xcode6Device> getDevices() {
    return devices;
  }


  @Override
  public XcodeDevice getDevice(XcodeRuntime rt, XcodeDeviceType type) {
    for (Xcode6Device device : devices) {
      if (device.getRuntime().equals(rt) && device.getDeviceType().equals(type)) {
        return device;
      }
    }
    throw new WebDriverException("Cannot find device from " + type + rt);
  }

  public Xcode6Runtime getRuntime(String sdkVersion) {
    for (Xcode6Runtime rt : runtimes) {
      if (rt.getVersion().equals(sdkVersion)) {
        return rt;
      }
    }
    throw new WebDriverException("cannot find runtime " + sdkVersion);
  }

  public XcodeDeviceType getDeviceType(DeviceVariation variation) {
    for (Xcode6DeviceType t : deviceTypes) {
      if (t.getShortName().replaceAll(" ","").equalsIgnoreCase(variation.toString())) {
        return t;
      }
    }
    throw new WebDriverException("Cannot find device type " + variation);
  }
}
