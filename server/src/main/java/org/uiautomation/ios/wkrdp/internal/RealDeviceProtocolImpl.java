/*
 * Copyright 2012 ios-driver committers.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the Licence at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License
 *  is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  or implied. See the License for the specific language governing permissions and limitations under
 *  the License.
 */

package org.uiautomation.ios.wkrdp.internal;

import org.libimobiledevice.binding.raw.IMobileDeviceFactory;
import org.libimobiledevice.binding.raw.IMobileDeviceService;
import org.libimobiledevice.binding.raw.IOSDevice;
import org.libimobiledevice.binding.raw.WebInspectorService;
import org.uiautomation.ios.wkrdp.MessageListener;
import org.uiautomation.ios.wkrdp.ResponseFinder;
import org.uiautomation.ios.wkrdp.message.IOSMessage;

import java.util.UUID;

/**
 * WKRDP implementation for real device using a USB connection.
 */
public class RealDeviceProtocolImpl extends WebKitRemoteDebugProtocol {

  private WebInspectorService inspector;

  public RealDeviceProtocolImpl(MessageListener listener,
                                ResponseFinder... finders) {
    super(listener, finders);
    IOSDevice d = IMobileDeviceFactory.INSTANCE.get("ff4827346ed6b54a98f51e69a261a140ae2bf6b3");
    inspector = new WebInspectorService(d);
    start();
  }

  public RealDeviceProtocolImpl(){
    super(new MessageListener() {
      @Override
      public void onMessage(IOSMessage message) {
        System.out.println(message);
      }
    });
    IOSDevice d = IMobileDeviceFactory.INSTANCE.get("ff4827346ed6b54a98f51e69a261a140ae2bf6b3");
    inspector = new WebInspectorService(d);
    start();
  }

  public static void main(String[] args){
    RealDeviceProtocolImpl r = new  RealDeviceProtocolImpl();
  }


  public static String key = UUID.randomUUID().toString();
  @Override
  public void start() {
    inspector.startWebInspector();
    startListenerThread();
    //inspector.sendMessage(
    /*sendMessage(
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n" +
                          "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">"
                          + "\n" +
                          "<plist version=\"1.0\">" + "\n" +
                          " <dict>" + "\n" +
                          " <key>__argument</key>" + "\n" +
                          " <dict>" + "\n" +
                          " <key>WIRConnectionIdentifierKey</key>" + "\n" +
                          //" <string>9128c1d9-069d-4751-b45b-bbcb6e7e8591</string>" + "\n" +
                          " <string>"+ key+"</string>" + "\n" +
                          " </dict>" + "\n" +
                          " <key>__selector</key>" + "\n" +
                          " <string>_rpc_reportIdentifier:</string>" + "\n" +
                          " </dict>" + "\n" +
                          "</plist>" + "\n");*/
    /*try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }*/
  }

  @Override
  public void stop() {
    stopListenerThread();
    inspector.stopWebInspector();
  }

  @Override
  protected void read() throws Exception {
    //System.out.println("Reading ...");
    String msg = inspector.receiveMessage();
    //System.out.println("Read : "+msg);

    if (msg != null) {
      handler.handle(msg);
    }
  }

  @Override
  protected void sendMessage(String message) {
    inspector.sendMessage(message);
  }
}
