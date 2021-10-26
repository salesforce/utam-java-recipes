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
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
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
 * Base Class for UTAM Mobile tests
 *
 * @author salesforce
 * @since 236
 */
public abstract class UtamMobileTestBase {

  private AppiumDriverLocalService appiumService;
  private AppiumCapabilityProvider desiredCapabilities;
  private WebDriver driver;
  private UtamLoaderConfig config;
  private UtamLoader loader;

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
  }

  protected final void setupAndroid() {
    setupMobile();
    System.setProperty("android.app", getUserHomeRelativePath("SApp.apk"));
    System.setProperty("app.activity", "com.salesforce.chatter.Chatter");
    driver = WebDriverFactory.getWebDriver(DriverType.android, appiumService, desiredCapabilities);
    config = new UtamLoaderConfigImpl("loader.config.json");
    config.setProfile(MobilePlatformType.fromDriver(driver));
    loader = new UtamLoaderImpl(config, WebDriverFactory.getAdapter(driver));
  }

  protected final void setupIOS() {
    setupMobile();
    System.setProperty("ios.device", "iPhone 8 Plus");
    System.setProperty("ios.app", getUserHomeRelativePath("SApp.app"));
    desiredCapabilities.setDesiredCapability(MobileCapabilityType.PLATFORM_VERSION, "13.4");
    driver = WebDriverFactory.getWebDriver(DriverType.ios, appiumService, desiredCapabilities);
    config = new UtamLoaderConfigImpl("loader.config.json");
    config.setProfile(MobilePlatformType.fromDriver(driver));
    loader = new UtamLoaderImpl(config, WebDriverFactory.getAdapter(driver));
  }

  /** Quit web driver if it's not null. Method is used in test or suite teardown */
  protected final void quitDriver() {
    if (driver != null) {
      driver.quit();
    }
    if (appiumService != null) {
      appiumService.stop();
    }
  }

  /**
   * helper method to load any Root Page Object
   *
   * @param rootPageObjectType type of the page object to load
   * @param <T> generic bound
   * @return instance of the loaded PO
   */
  protected <T extends RootPageObject> T from(Class<T> rootPageObjectType) {
    return loader.load(rootPageObjectType);
  }

  protected final void setBridgeAppTitle(String title) {
    config.setBridgeAppTitle(title);
    loader.resetContext();
  }

  /**
   * get instance of the driver
   *
   * @return instance of the web driver
   */
  protected final WebDriver getDriver() {
    return driver;
  }

  /**
   * method that waits for hardcoded time, only for debug
   *
   * @param seconds seconds to sleep for
   */
  protected final void debug(int seconds) {
    try {
      Thread.sleep(seconds * 1000);
    } catch (InterruptedException ie) {
      throw new AssertionError(ie);
    }
  }

  /**
   * helper method to print information to console
   *
   * @param str message to print
   */
  protected final void log(String str) {
    Reporter.log("Utam log: " + str);
  }
}
