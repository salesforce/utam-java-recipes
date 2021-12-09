/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: MIT
 * For full license text, see the LICENSE file in the repo root
 * or https://opensource.org/licenses/MIT
 */
package utam.examples.portal;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utam.base.UtamWebTestBase;
import utam.portal.pageobjects.UtamDevHome;

/**
 * Example of test for https://utam.dev
 *
 * @author Salesforce
 * @since 236
 */
public class UtamPortalTests extends UtamWebTestBase {

  @BeforeTest
  public void setup() {
    setupFirefox();
  }

  @Test
  public void testMenu() {
    getDriver().get("https://utam.dev");
    UtamDevHome homePage = from(UtamDevHome.class);
    Assert.assertTrue(homePage.getMenuItems().size() == 3);
  }

  @AfterTest
  public final void tearDown() {
    quitDriver();
  }
}
