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
import utam.core.selenium.element.LocatorBy;
import utam.portal.pageobjects.UtamDevHome;
import utam.tests.pageobjects.Login;
import utam.tests.pageobjects.NullableExample;

/**
 * Example of test for https://utam.dev
 *
 * @author Salesforce
 * @since Dec 2021
 */
public class UtamPortalTests extends UtamWebTestBase {

  @BeforeTest
  public void setup() {
    setupFirefox();
  }

  @Test
  public void testMenu() {
    log("Navigate to portal");
    getDriver().get("https://utam.dev");

    log("Load Home Page");
    UtamDevHome homePage = from(UtamDevHome.class);

    Assert.assertTrue(homePage.getMenuItems().size() == 3, "number of menu items");
    homePage.getGrammarMenuItem().click();
    Assert.assertEquals(getDomDocument().getUrl(), "https://utam.dev/grammar/spec");
  }

  @Test
  public void testNullableExample() {
    log("Navigate to portal");
    getDriver().get("https://utam.dev");

    log("Assert that root page is not loaded");
    assert getDomDocument().containsObject(Login.class) == false;

    log("Assert that root element with a given locator is not present");
    assert getDomDocument().containsElement(LocatorBy.byCss("idonotexist")) == false;

    log("Load Home Page");
    NullableExample homePage = from(NullableExample.class);
    log("Assert that page object does not have an element with a given locator");
    assert homePage.containsElement(LocatorBy.byCss("idonotexist")) == false;

    log("Assert that non existing nullable basic element is returned as null");
    assert homePage.getNullableBasicElement() == null;

    log("Assert that non existing nullable basic elements list is returned as null");
    assert homePage.getNullableBasicElementList() == null;

    log("Assert that non existing nullable custom element is returned as null");
    assert homePage.getNullableCustomElement() == null;

    log("Assert that non existing nullable custom elements list is returned as null");
    assert homePage.getNullableCustomElementList() == null;

    log("Assert that nullable element scoped inside non existing nullable basic element is returned as null");
    assert homePage.getScopedInsideNullable() == null;
  }

  @AfterTest
  public final void tearDown() {
    quitDriver();
  }
}
