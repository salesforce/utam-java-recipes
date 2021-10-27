/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: MIT
 * For full license text, see the LICENSE file in the repo root
 * or https://opensource.org/licenses/MIT
 */
package utam.base;

import java.util.ResourceBundle;
import utam.tests.pageobjects.Login;

/**
 * Base Class for Web tests
 *
 * @author salesforce
 * @since 236
 */
public abstract class SalesforceWebTestBase extends UtamWebTestBase {

  protected final void login(String envName, String homePageUrl) {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("env");
    String urlKey = envName + ".url";
    String usernameKey = envName + ".username";
    String passwordKey = envName + ".password";
    getDriver().get(resourceBundle.getString(urlKey));
    Login loginPage = from(Login.class);
    loginPage.login(resourceBundle.getString(usernameKey), resourceBundle.getString(passwordKey), homePageUrl);
  }


}
