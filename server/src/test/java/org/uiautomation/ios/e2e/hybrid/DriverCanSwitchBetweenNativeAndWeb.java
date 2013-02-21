package org.uiautomation.ios.e2e.hybrid;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.uiautomation.ios.BaseIOSDriverTest;
import org.uiautomation.ios.IOSCapabilities;
import org.uiautomation.ios.SampleApps;
import org.uiautomation.ios.UIAModels.UIAButton;
import org.uiautomation.ios.UIAModels.UIAElement;
import org.uiautomation.ios.UIAModels.UIATableCell;
import org.uiautomation.ios.UIAModels.predicate.AndCriteria;
import org.uiautomation.ios.UIAModels.predicate.MatchingStrategy;
import org.uiautomation.ios.UIAModels.predicate.NameCriteria;
import org.uiautomation.ios.UIAModels.predicate.TypeCriteria;
import org.uiautomation.ios.client.uiamodels.impl.RemoteUIADriver;

import java.util.Set;

public class DriverCanSwitchBetweenNativeAndWeb extends BaseIOSDriverTest {

  @Test
  public void canSwitchToWebView() throws Exception {
    IOSCapabilities safari = IOSCapabilities.iphone("UICatalog");
    safari.setCapability(IOSCapabilities.TIME_HACK, false);

    RemoteUIADriver driver = null;
    try {
      driver = new RemoteUIADriver(getRemoteURL(), SampleApps.uiCatalogCap());
      Set<String> handles = driver.getWindowHandles();
      Assert.assertEquals(handles.size(), 1);

      try {
        driver.switchTo().window("Web");
        Assert.fail("shouldn't work");
      } catch (NoSuchWindowException e) {
        // expected
      }
      UIAElement
          el =
          driver.findElement(
              new AndCriteria(new TypeCriteria(UIATableCell.class), new NameCriteria("Web",
                                                                                     MatchingStrategy.starts)));
      el.tap();

      while (driver.getWindowHandles().size() != 2) {
        Thread.sleep(50);
      }
      handles = driver.getWindowHandles();
      Assert.assertEquals(handles.size(), 2);

      UIAElement back = driver
          .findElement(
              new AndCriteria(new TypeCriteria(UIAButton.class), new NameCriteria("Back")));
      back.tap();
      handles = driver.getWindowHandles();
      Assert.assertEquals(handles.size(), 1);

    } finally {
      driver.quit();
    }
  }

  @Test
  public void canSwitchToTheWebViewAndFindByCSS() throws Exception {
    IOSCapabilities safari = IOSCapabilities.iphone("UICatalog");
    safari.setCapability(IOSCapabilities.TIME_HACK, false);

    RemoteUIADriver driver = null;
    try {
      driver = new RemoteUIADriver(getRemoteURL(), SampleApps.uiCatalogCap());
      Set<String> handles = driver.getWindowHandles();
      Assert.assertEquals(handles.size(), 1);
      UIAElement
          webCell =
          driver.findElement(new AndCriteria(new TypeCriteria(UIATableCell.class), new NameCriteria(
              "Web", MatchingStrategy.starts)));
      webCell.tap();

      handles = driver.getWindowHandles();
      Assert.assertEquals(handles.size(), 2);
      driver.switchTo().window("Web");

      final By by = By.cssSelector("a[href='http://store.apple.com/']");

      long end = System.currentTimeMillis() + 10000;
      WebElement el;
      while (System.currentTimeMillis() < end) {
        try {
          el = driver.findElement(by);
          break;
        } catch (NoSuchElementException e) {
          // ignore
          System.out.println("Cannot find element yet.");
        }
      }
      el = driver.findElement(by);
      //el.click();
      Assert.assertEquals(el.getAttribute("href"), "http://store.apple.com/");
      System.out.println("HREF=" + el.getAttribute("href"));
      WebElement body = driver.findElement(By.cssSelector("body"));
      System.out.println(body.getText());

      driver.get("http://ebay.co.uk");
      WebElement search = driver.findElement(By.id("kw"));

      search.sendKeys("ipod");
      body = driver.findElement(By.cssSelector("body"));

      System.out.println(body.getText());
      System.out.println(body.getText());
    } finally {
      driver.quit();
    }

  }

  @Test//(enabled = false)
  public void nativeWebViewSeesNewPages() throws Exception {
    IOSCapabilities safari = IOSCapabilities.iphone("UICatalog");
    safari.setCapability(IOSCapabilities.TIME_HACK, false);

    RemoteUIADriver driver = null;
    try {
      driver = new RemoteUIADriver(getRemoteURL(), SampleApps.uiCatalogCap());
      Set<String> handles = driver.getWindowHandles();
      Assert.assertEquals(handles.size(), 1);
      UIAElement
          webCell =
          driver.findElement(new AndCriteria(new TypeCriteria(UIATableCell.class), new NameCriteria(
              "Web", MatchingStrategy.starts)));
      webCell.tap();

      driver.switchTo().window("Web");
      Thread.sleep(5000);
      driver.get("http://ebay.co.uk");
      WebElement body = driver.findElement(By.cssSelector("body"));
      System.out.println(body.getText());


     /* final By by = By.id("v4-1");

      long end = System.currentTimeMillis() + 5000;
      WebElement el;
      while (System.currentTimeMillis() < end) {
        try {
          el = driver.findElement(by);
          break;
        } catch (NoSuchElementException e) {
          // ignore
        }
      }
      el = driver.findElement(by);
      System.out.println(el.getText());   */
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (driver != null) {
        driver.quit();
      }
    }

  }

}