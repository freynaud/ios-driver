package org.uiautomation.ios.client.uiamodels.impl;

import org.openqa.selenium.TouchScreen;
import org.uiautomation.ios.client.uiamodels.impl.configuration.WebDriverLikeCommandExecutor;

public class NoOpNativeDriver extends RemoteIOSDriver {

  public NoOpNativeDriver(){
    super((WebDriverLikeCommandExecutor)null,(TouchScreen)null);
  }
}
