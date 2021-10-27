/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: MIT
 * For full license text, see the LICENSE file in the repo root
 * or https://opensource.org/licenses/MIT
 */
package utam.base;

import static utam.base.UtamMobileTestBase.getUserHomeRelativePath;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import utam.core.driver.Document;
import utam.core.driver.DriverType;
import utam.core.framework.base.RootPageObject;
import utam.core.framework.consumer.UtamLoader;
import utam.core.framework.consumer.UtamLoaderConfig;
import utam.core.framework.consumer.UtamLoaderConfigImpl;
import utam.core.framework.consumer.UtamLoaderImpl;
import utam.core.selenium.factory.WebDriverFactory;

/**
 * Base Class for Web tests
 *
 * @author salesforce
 * @since 236
 */
public abstract class UtamWebTestBase {

  private WebDriver driver;
  private UtamLoader loader;

  private UtamLoaderConfig getLoaderConfig() {
    UtamLoaderConfig config = new UtamLoaderConfigImpl("loader.config.json");
    config.setWaitForTimeout(Duration.ofSeconds(90));
    config.setFindTimeout(Duration.ofSeconds(30));
    return config;
  }

  protected final Document getDomDocument() {
    return loader.getDocument();
  }

  protected final void setupChrome() {
    System.setProperty("webdriver.chrome.driver",  getUserHomeRelativePath("chromedriver"));
    driver = WebDriverFactory.getWebDriver(DriverType.chrome);
    UtamLoaderConfig config = getLoaderConfig();
    loader = new UtamLoaderImpl(config, WebDriverFactory.getAdapter(driver));
  }

  protected final void setupFirefox() {
    System.setProperty("webdriver.gecko.driver", getUserHomeRelativePath("geckodriver"));
    driver = WebDriverFactory.getWebDriver(DriverType.firefox);
    UtamLoaderConfig config = getLoaderConfig();
    loader = new UtamLoaderImpl(config, WebDriverFactory.getAdapter(driver));
  }

  // helper method to load any Root Page Object
  protected final <T extends RootPageObject> T from (Class<T> rootPage) {
    return loader.load(rootPage);
  }

  @AfterTest
  public final void tearDown() {
    quitDriver();
  }

  protected final void quitDriver() {
    if (driver != null) {
      driver.quit();
    }
  }

  protected final WebDriver getDriver() {
    return driver;
  }

  protected final void debug(int seconds) {
    try {
      Thread.sleep(seconds * 1000);
    } catch (InterruptedException ie) {
      throw new AssertionError(ie);
    }
  }

  protected final void log(String str) {
    System.out.println(str);
  }
}
