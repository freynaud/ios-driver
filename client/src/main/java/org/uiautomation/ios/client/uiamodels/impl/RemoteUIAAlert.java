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
package org.uiautomation.ios.client.uiamodels.impl;

import com.google.common.collect.ImmutableMap;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.security.Credentials;
import org.uiautomation.ios.UIAModels.UIAAlert;
import org.uiautomation.ios.UIAModels.UIAButton;
import org.uiautomation.ios.UIAModels.UIAElement;
import org.uiautomation.ios.UIAModels.UIAStaticText;
import org.uiautomation.ios.UIAModels.predicate.TypeCriteria;
import org.uiautomation.ios.communication.WebDriverLikeCommand;
import org.uiautomation.ios.communication.WebDriverLikeRequest;

import java.util.List;

public class RemoteUIAAlert extends RemoteUIAElement implements UIAAlert, Alert {

  public RemoteUIAAlert(RemoteWebDriver driver, String reference) {
    super(driver, reference);

  }

  @Override
  public UIAButton getCancelButton() {
    WebDriverLikeRequest request = buildRequest(WebDriverLikeCommand.ALERT_CANCEL_BUTTON);
    return commandExecutor.execute(request);

  }

  @Override
  public UIAButton getDefaultButton() {
    WebDriverLikeRequest request = buildRequest(WebDriverLikeCommand.ALERT_DEFAULT_BUTTON);
    UIAButton butt = commandExecutor.execute(request);
    return butt;
  }

  @Override
  public void dismiss() {
    WebDriverLikeRequest request = buildRequest(WebDriverLikeCommand.DISMISS_ALERT);
    commandExecutor.execute(request);

  }

  @Override
  public void accept() {
    WebDriverLikeRequest request = buildRequest(WebDriverLikeCommand.ACCEPT_ALERT);
    commandExecutor.execute(request);
  }

  @Override
  public void sendKeys(String keysToSend) {
    WebDriverLikeRequest
        request =
        buildRequest(WebDriverLikeCommand.SET_ALERT_TEXT, ImmutableMap.of("text", keysToSend));
    commandExecutor.execute(request);
  }

  @Override
  public void setCredentials(Credentials credentials) {
    // TODO freynaud
    throw new RuntimeException("NI");
  }

  @Override
  public void authenticateUsing(Credentials credentials) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public String getText() {
    WebDriverLikeRequest request = buildRequest(WebDriverLikeCommand.GET_ALERT_TEXT);
    return commandExecutor.execute(request);
  }


}
