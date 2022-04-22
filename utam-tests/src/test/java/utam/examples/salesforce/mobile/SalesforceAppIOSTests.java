/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: MIT
 * For full license text, see the LICENSE file in the repo root
 * or https://opensource.org/licenses/MIT
 */
package utam.examples.salesforce.mobile;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utam.base.UtamMobileTestBase;
import utam.helpers.pageobjects.Login;
import utam.salesforceapp.pageobjects.authentication.AddConn;
import utam.salesforceapp.pageobjects.authentication.ChooseConn;
import utam.salesforceapp.pageobjects.authentication.Eula;
import utam.salesforceapp.pageobjects.authentication.LoginNavBar;

public class SalesforceAppIOSTests extends UtamMobileTestBase {

  @BeforeTest
  public void setUp() {
    setupIOS();
  }

  @Test
  public void testSetDataConnection() {
    from(Eula.class).accept();

    LoginNavBar navBar = from(LoginNavBar.class);
    navBar.chooseConnOption();

    ChooseConn chooseConnection = from(ChooseConn.class);
    chooseConnection.addNewConnection();

    AddConn addConnection = from(AddConn.class);
    addConnection.isVisible();
    addConnection.addConnection("local", "login.salesforce.com");

    setBridgeAppTitle("Login | Salesforce");

    log("Navigate back via the soft back button");
    getDriver().navigate().back();

    log("Load Login page");
    from(Login.class);
  }

  @AfterTest
  public final void tearDown() {
    quitDriver();
  }
}
