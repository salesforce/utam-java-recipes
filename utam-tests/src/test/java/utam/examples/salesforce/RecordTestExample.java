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

public class RecordTestExample extends SalesforceWebTestBase {

  @BeforeTest
  public void setup() {
    setupChrome();
    TestEnvironmentUtils testEnvironment = getTestEnvironment("myOrg");
    // login by url
    getDriver().get(testEnvironment.getLoginUrl());
  }

  @AfterTest
  public final void tearDown() {
    quitDriver();
  }

  @Test
  public void testRecordCreation() {
    debug(10);
  }
}
