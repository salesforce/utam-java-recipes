/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: MIT
 * For full license text, see the LICENSE file in the repo root
 * or https://opensource.org/licenses/MIT
 */
package utam.base;

import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import utam.core.driver.DriverType;
import utam.core.framework.base.RootPageObject;
import utam.core.framework.consumer.UtamLoader;
import utam.core.framework.consumer.UtamLoaderConfig;
import utam.core.framework.consumer.UtamLoaderConfigImpl;
import utam.core.framework.consumer.UtamLoaderImpl;
import utam.core.framework.context.MobilePlatformType;
import utam.core.selenium.factory.AppiumCapabilityProvider;
import utam.core.selenium.factory.AppiumServerFactory;
import utam.core.selenium.factory.WebDriverFactory;

/**
 * Base Class for Mobile tests
 *
 * @author salesforce
 * @since 236
 */
public abstract class UtamMobileTestBase {

  AppiumDriverLocalService appiumService;
  AppiumCapabilityProvider desiredCapabilities;
  WebDriver driver;
  UtamLoaderConfig config;
  UtamLoader loader;

  static String getUserHomeRelativePath(String fileName) {
    return System.getProperty("user.home") + System.getProperty("file.separator") + fileName;
  }

  /** setup shared by all mobile platforms */
  private void setupMobile() {
    System.setProperty("nodejs", "/usr/local/bin/node");
    System.setProperty("appium", "/usr/local/lib/node_modules/appium/");
    System.setProperty("app.bundleid", "com.salesforce.chatter");

    appiumService = AppiumServerFactory.getAppiumServer();
    desiredCapabilities = new AppiumCapabilityProvider();
    // Force to use MJSONWP instead of default W3C
    desiredCapabilities.setDesiredCapability(MobileCapabilityType.FORCE_MJSONWP, true);

    config = new UtamLoaderConfigImpl("loader.config.json");
    config.setWaitForTimeout(Duration.ofSeconds(40));
    config.setFindTimeout(Duration.ofSeconds(20));
  }

  protected final void setupAndroid(String bridgeAppTitle) {
    setupMobile();
    System.setProperty("android.app", getUserHomeRelativePath("SApp.apk"));
    System.setProperty("app.activity", "com.salesforce.chatter.Chatter");
    driver = WebDriverFactory.getWebDriver(DriverType.android, appiumService, desiredCapabilities);
    config.setProfile(MobilePlatformType.fromDriver(driver));
    config.setBridgeAppTitle(bridgeAppTitle);
    loader = new UtamLoaderImpl(config, WebDriverFactory.getAdapter(driver));
  }

  protected final void setupIOS(String bridgeAppTitle) {
    setupMobile();
    System.setProperty("ios.device", "iPhone 8 Plus");
    System.setProperty("ios.app", getUserHomeRelativePath("SApp.app"));
    desiredCapabilities.setDesiredCapability(MobileCapabilityType.PLATFORM_VERSION, "13.4");
    driver = WebDriverFactory.getWebDriver(DriverType.ios, appiumService, desiredCapabilities);
    config.setProfile(MobilePlatformType.fromDriver(driver));
    config.setBridgeAppTitle(bridgeAppTitle);
    loader = new UtamLoaderImpl(config, WebDriverFactory.getAdapter(driver));
  }

  @AfterTest
  public final void quitDriver() {
    if (driver != null) {
      driver.quit();
    }
    if (appiumService != null) {
      appiumService.stop();
    }
  }

  protected <T extends RootPageObject> T from(Class<T> type) {
    return loader.load(type);
  }

  protected final void setBridgeAppTitle(String title) {
    config.setBridgeAppTitle(title);
    loader.resetContext();
  }

  protected final WebDriver getDriver() {
    return driver;
  }

  protected void setDataConnection() {
    // this section needs published POs
    /*
    from(Eula.class).accept();

    LoginNavBar navBar = from(LoginNavBar.class);
    navBar.chooseConnOption();

    if (driver instanceof AndroidDriver) {
        LoginNavBarOptions navBarOption = from(LoginNavBarOptions.class);
        navBarOption.changeServer();
    }

    ChooseConn chooseConnection = from(ChooseConn.class);
    chooseConnection.addNewConnection();

    AddConn addConnection = from(AddConn.class);
    addConnection.isVisible();
    addConnection.addConnection("local", "login.salesforce.com");
     */
  }
}
