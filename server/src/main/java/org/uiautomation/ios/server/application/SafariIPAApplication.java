package org.uiautomation.ios.server.application;

import java.util.ArrayList;
import java.util.List;

public class SafariIPAApplication extends IPAApplication {

  public SafariIPAApplication() {
    // TODO freynaud archive and load from there.
  }

  @Override
  List<AppleLocale> getSupportedLanguages() {
    // TODO freynaud for now.
    return new ArrayList<>();
  }

  @Override
  public String getBundleId(){
    return "com.apple.mobilesafari";
  }

}
