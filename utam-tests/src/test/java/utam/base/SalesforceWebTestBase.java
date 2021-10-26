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
public abstract class SalesforceWebTestBase extends UtamWebTestBase {

  protected final void login(String login, String password, String homePageUrl) {

  }
}
