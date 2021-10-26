/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: MIT
 * For full license text, see the LICENSE file in the repo root
 * or https://opensource.org/licenses/MIT
 */
package utam.examples.salesforce.web;

import utam.base.UtamWebTestBase;
import utam.tests.pageobjects.Login;
import utam.utils.salesforce.TestEnvironment;

/**
 * Base Class for Salesforce Web tests with login utilities
 *
 * @author salesforce
 * @since 236
 */
abstract class SalesforceWebTestBase extends UtamWebTestBase {

  /**
   * login to the environment based on url and credentials provided in env.properties file which
   * should be located in test resources root
   *
   * @param testEnvironment environment information
   * @param landingPagePartialUrl after login, this is partial url that we land in
   */
  public final void login(TestEnvironment testEnvironment, String landingPagePartialUrl) {
    final String baseUrl = testEnvironment.getBaseUrl();
    final String userName = testEnvironment.getUserName();
    log("Navigate to login URL: " + baseUrl);
    getDriver().get(baseUrl);
    Login loginPage = from(Login.class);
    log(
        String.format(
            "Enter username '%s' and password, wait for landing page Url containing '%s'",
            userName, landingPagePartialUrl));
    loginPage.loginToHomePage(userName, testEnvironment.getPassword(), landingPagePartialUrl);
  }

  /**
   * same as login method, but with hardcoded expected partial URL "home"
   *
   * @param testEnvironment environment information
   */
  public final void loginToHomePage(TestEnvironment testEnvironment) {
    login(testEnvironment, "home");
  }
}
