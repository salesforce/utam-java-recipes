/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: MIT
 * For full license text, see the LICENSE file in the repo root
 * or https://opensource.org/licenses/MIT
 */
package utam.base;

import utam.examples.TestEnvironmentUtils;
import utam.tests.pageobjects.Login;

/**
 * Base Class for Web tests
 *
 * @author salesforce
 * @since 236
 */
public abstract class SalesforceWebTestBase extends UtamWebTestBase {

  protected final TestEnvironmentUtils getTestEnvironment(String envNamePrefix) {
    return new TestEnvironmentUtils(envNamePrefix);
  }

  /**
   * login to the environment based on url and credentials provided in env.properties file which
   * should be located in test resources root
   *
   * @param envNamePrefix prefix
   * @param landingPagePartialUrl after login, this is partial url that we land in
   * @return base url defined in properties file
   */
  protected final TestEnvironmentUtils login(String envNamePrefix, String landingPagePartialUrl) {
    TestEnvironmentUtils testEnvironment = getTestEnvironment(envNamePrefix);
    String baseUrl = testEnvironment.getBaseUrl();
    getDriver().get(baseUrl);
    Login loginPage = from(Login.class);
    loginPage.login(
        testEnvironment.getUserName(), testEnvironment.getPassword(), landingPagePartialUrl);
    return testEnvironment;
  }

  protected final TestEnvironmentUtils loginToHomePage(String envNamePrefix) {
    return login(envNamePrefix, "home");
  }
}
