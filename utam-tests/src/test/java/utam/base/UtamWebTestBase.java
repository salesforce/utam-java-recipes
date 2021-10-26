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
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
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

  protected final void setupChrome() {
    System.setProperty("webdriver.chrome.driver",  getUserHomeRelativePath("chromedriver"));
    driver = WebDriverFactory.getWebDriver(DriverType.chrome);
    UtamLoaderConfig config = new UtamLoaderConfigImpl("loader.config.json");
    config.setWaitForTimeout(Duration.ofSeconds(90));
    config.setFindTimeout(Duration.ZERO);
    loader = new UtamLoaderImpl(config, WebDriverFactory.getAdapter(driver));
    driver.get("https://na45.stmfa.stm.salesforce.com");
  }

  // helper method to load any Root Page Object
  protected final <T extends RootPageObject> T from (Class<T> rootPage) {
    return loader.load(rootPage);
  }

  @AfterTest
  public final void quitDriver() {
    if (driver != null) {
      driver.quit();
    }
  }

  protected final WebDriver getDriver() {
    return driver;
  }
}
