/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: MIT
 * For full license text, see the LICENSE file in the repo root
 * or https://opensource.org/licenses/MIT
 */
package utam.examples.salesforce;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utam.base.SalesforceWebTestBase;

public class RecordTestExample extends SalesforceWebTestBase {

  @BeforeTest
  public void setUp() {
    setupChrome();
    getDriver().get("https://www.salesforce.com");
    login("myLogin", "myPassword", "home");
  }

  @Test
  public void testRecordCreation() {

  }
}
