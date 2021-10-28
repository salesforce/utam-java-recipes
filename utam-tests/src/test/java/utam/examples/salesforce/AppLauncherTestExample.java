/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: MIT
 * For full license text, see the LICENSE file in the repo root
 * or https://opensource.org/licenses/MIT
 */
package utam.examples.salesforce;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utam.base.SalesforceWebTestBase;
import utam.examples.TestEnvironmentUtils;

public class AppLauncherTestExample extends SalesforceWebTestBase {

  @BeforeTest
  public void setup() {
    setupChrome();
    TestEnvironmentUtils testEnvironment = getTestEnvironment("myOrg");
    // login by url
    getDriver().get(testEnvironment.getLoginUrl());
  }

  @Test
  public void testSwitchToSalesApp() {
    // todo
    debug(10);
  }

  @AfterTest
  public final void tearDown() {
    quitDriver();
  }
}
