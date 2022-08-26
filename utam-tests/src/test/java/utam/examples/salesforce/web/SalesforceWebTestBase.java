/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: MIT
 * For full license text, see the LICENSE file in the repo root
 * or https://opensource.org/licenses/MIT
 */
package utam.examples.salesforce.web;

import utam.base.UtamWebTestBase;
import utam.core.driver.Document;
import utam.core.framework.context.StringValueProfile;
import utam.helpers.pageobjects.Login;
import utam.utils.salesforce.RecordType;
import utam.utils.salesforce.TestEnvironment;

/**
 * Base Class for Salesforce Web tests with login utilities
 *
 * @author salesforce
 * @since Dec 2021
 */
abstract class SalesforceWebTestBase extends UtamWebTestBase {

  /**
   * login to the environment based on url and credentials provided in env.properties file which
   * should be located in test resources root
   *
   * @param testEnvironment environment information
   * @param landingPagePartialUrl after login, this is partial url that we land in
   */
  final void login(TestEnvironment testEnvironment, String landingPagePartialUrl) {
    final String baseUrl = testEnvironment.getBaseUrl();
    final String userName = testEnvironment.getUserName();
    log("Navigate to login URL: " + baseUrl);
    getDriver().get(baseUrl);
    Login loginPage = from(Login.class);
    log(
        String.format(
            "Enter username '%s' and password, wait for landing page Url containing '%s'",
            userName, landingPagePartialUrl));
    loginPage.login(userName, testEnvironment.getPassword());
    Document document = getDomDocument();
    document.waitFor(() -> document.getUrl().contains(landingPagePartialUrl));
  }

  final void setProfile(RecordType recordType) {
    loader.getConfig().setProfile(new StringValueProfile("entity", recordType.name().toLowerCase()));
    loader.resetContext();
  }
}
