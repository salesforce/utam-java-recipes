/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: MIT
 * For full license text, see the LICENSE file in the repo root
 * or https://opensource.org/licenses/MIT
 */
package utam.examples.salesforce.web;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utam.sfdx.pageobjects.Hello;
import utam.sfdx.pageobjects.HomePage;
import utam.sfdx.pageobjects.WireGetObjectInfo;
import utam.utils.salesforce.TestEnvironment;

/**
 * Test for a scratch org. See README for setup.
 *
 * @author Salesforce
 * @since Dec 2021
 */
public class SfdxScratchOrgTests extends SalesforceWebTestBase {

  private HomePage appHomePage;

  @BeforeTest
  public void loginToRecipeApp() {
    setupChrome();
    TestEnvironment testEnvironment = getTestEnvironment("scratchOrg");
    log("Navigate to login URL for a scratch org");
    getDriver().get(testEnvironment.getSfdxLoginUrl());
    log("Wait for Home Page URL");
    getDomDocument().waitFor(() -> getDomDocument().getUrl().contains("Hello"));
    log("Wait for Application Home Page to load");
    appHomePage = from(HomePage.class);
  }

  @Test
  public void testHelloComponent() {
    log("Wait for Flexipage with Hello component to load");
    Hello hello = appHomePage.getComponent().getContent(Hello.class);

    log("Get and assert text inside the component");
    String expectedText = hello.getText();
    assert expectedText.contains("Hello, World!");
  }

  @Test
  public void testWireComponent() {
    log("Click 'Wire' in app navigation menu and wait for URL navigation");
    appHomePage
        .getNavigationBar()
        .getAppNavBar()
        .getNavItem("Wire")
        .clickAndWaitForUrl("lightning/n/Wire");

    log("Wait for Flexipage with Wire component to load");
    WireGetObjectInfo wireInfo = appHomePage.getComponent().getContent(WireGetObjectInfo.class);

    log("Enter search criteria and click 'Search', wait for response");
    wireInfo.searchAndWaitForResponse("Contact");

    log("Get and assert response content");
    String text = wireInfo.getContent();
    assert text.contains("\"apiName\": \"Contact\"");
  }

  @AfterTest
  public final void tearDown() {
    quitDriver();
  }
}
