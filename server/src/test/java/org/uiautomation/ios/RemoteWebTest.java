package org.uiautomation.ios;

import org.testng.annotations.Test;
import org.uiautomation.ios.drivers.RemoteIOSWebDriver;
import org.uiautomation.ios.wkrdp.WebKitRemoteDebugProtocolFactory;
import org.uiautomation.ios.wkrdp.internal.SimulatorProtocolImpl;
import org.uiautomation.ios.wkrdp.internal.WebKitRemoteDebugProtocol;
import org.uiautomation.ios.wkrdp.message.WebkitApplication;

import java.util.List;

/**
 * Created by freynaud on 05/05/2016.
 */
public class RemoteWebTest {


  @Test
  public void test() throws InterruptedException {
    ServerSideSession session = new ServerSideSession(null,IOSCapabilities.iphone("com.apple.mobilesafari"),null);
    WebKitRemoteDebugProtocol impl = new SimulatorProtocolImpl(session);

    RemoteIOSWebDriver webDriver = new RemoteIOSWebDriver(session,impl);
    //List<WebkitApplication> applications = webDriver.waitForTargetApplicationList();
    //webDriver = new RemoteIOSWebDriver(session, impl);
    //webDriver.setApplications(applications);
    webDriver.start();
    webDriver.switchTo(String.valueOf(1));
    webDriver.get("http://element34.net");
  }
}
