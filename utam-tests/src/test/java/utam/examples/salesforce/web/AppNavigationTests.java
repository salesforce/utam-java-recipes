/*
 * Copyright (c) 2022, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: MIT
 * For full license text, see the LICENSE file in the repo root
 * or https://opensource.org/licenses/MIT
 */
package utam.examples.salesforce.web;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utam.global.pageobjects.AppNavBar;
import utam.navex.pageobjects.DesktopLayoutContainer;
import utam.utils.salesforce.TestEnvironment;

/**
 * IMPORTANT: Page objects and tests for Salesforce UI are compatible with the application version
 * mentioned in published page objects Test environment is private SF sandbox, not available for
 * external users and has DEFAULT org setup
 *
 * @author Salesforce
 * @since June 2022
 */
public class AppNavigationTests extends SalesforceWebTestBase {

  private final TestEnvironment testEnvironment = getTestEnvironment("sandbox44");

  @BeforeTest
  public void setup() {
    setupChrome();
    login(testEnvironment, "home");
  }

  @Test
  public void testNavigateToNanBarItem() {
    getDriver().get(testEnvironment.getRedirectUrl());
    log("Load Desktop layout container");
    DesktopLayoutContainer layoutContainer = from(DesktopLayoutContainer.class);

    log("Navigate to nav bar item accounts");
    AppNavBar navBar = layoutContainer.getAppNav().getAppNavBar();
    navBar.getNavItem("Account").clickAndWaitForUrl("Account");
  }

  @Test
  public void testNavigateToNanBarOverflowItem() {
    getDriver().get(testEnvironment.getRedirectUrl());
    log("Load Desktop layout container");
    DesktopLayoutContainer layoutContainer = from(DesktopLayoutContainer.class);

    AppNavBar navBar = layoutContainer.getAppNav().getAppNavBar();
    log("Navigate to overflow menu item");
    navBar.getShowMoreMenuButton().expand();
    // menu item with name 'Forecasts' should present in overflow items
    navBar.getShowMoreMenuButton().getMenuItemByText("Forecasts").clickAndWaitForUrl("forecasting");
  }

  @AfterTest
  public final void tearDown() {
    quitDriver();
  }
}
