/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: MIT
 * For full license text, see the LICENSE file in the repo root
 * or https://opensource.org/licenses/MIT
 */
package utam.examples.portal;

import static org.testng.Assert.expectThrows;

import org.openqa.selenium.StaleElementReferenceException;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utam.base.UtamWebTestBase;
import utam.core.element.BasicElement;
import utam.core.selenium.element.LocatorBy;
import utam.portal.pageobjects.Dummy;
import utam.portal.pageobjects.NullableExample;
import utam.portal.pageobjects.UtamDevHome;

/**
 * Example of tests for https://utam.dev
 *
 * @author Salesforce
 * @since Dec 2021
 */
public class UtamPortalTests extends UtamWebTestBase {

  @BeforeTest
  public void setup() {
    setupChrome();
  }

  private UtamDevHome navigateToPortalHome() {

    log("Navigate to portal");
    getDriver().get("https://utam.dev");

    log("Load Home Page");
    return from(UtamDevHome.class);
  }

  /** Navigate to home page, click Grammar Spec link and validate URL */
  @Test
  public void testMenuGrammarLink() {
    UtamDevHome homePage = navigateToPortalHome();
    Assert.assertEquals(homePage.getMenuItems().size(), 6, "number of menu items");
    homePage.getGrammarMenuItem().click();
    Assert.assertEquals(getDomDocument().getUrl(), "https://utam.dev/grammar/spec");
  }

  /** Example of checking elements and page object presence in the root */
  @Test
  public void testRootElementPresence() {
    navigateToPortalHome();

    log("Assert that a root page is not loaded");
    // random root page object that we know is not present
    assert !getDomDocument().containsObject(Dummy.class);

    log("Assert that a root element with a given locator is not present");
    assert !getDomDocument().containsElement(LocatorBy.byCss("idonotexist"));
  }

  /** Example of checking absence of the elements on the page */
  @Test
  public void testNullableExample() {
    navigateToPortalHome();

    log("Load Home Page");
    NullableExample homePage = from(NullableExample.class);

    log("Non existing nullable basic element is returned as null");
    assert homePage.getNullableBasicElement() == null;

    log("Non existing nullable basic elements list is returned as null");
    assert homePage.getNullableBasicElementList() == null;

    log("Non existing nullable custom element is returned as null");
    assert homePage.getNullableCustomElement() == null;

    log("Non existing nullable custom elements list is returned as null");
    assert homePage.getNullableCustomElementList() == null;

    log("Nullable element scoped inside non existing nullable basic element is returned as null");
    assert homePage.getScopedInsideNullable() == null;
  }

  /** example of an element to become stale */
  @Test
  public void testStaleElementExample() {
    final UtamDevHome homePage = navigateToPortalHome();

    log("Confirm that everything is present and visible");
    homePage.waitForVisible();
    assert homePage.isPresent();
    assert homePage.isVisible();

    log("Get home page content, save as a variable");
    BasicElement pageContent = homePage.getContent();
    assert pageContent.isPresent();

    log("Reload web page by navigating to its URL again");
    getDriver().get("https://utam.dev");

    log("Because we reloaded content, all elements became stale");
    homePage.waitForAbsence();
    assert !homePage.isPresent();
    pageContent.waitForAbsence();
    assert !pageContent.isPresent();

    log("Attempt to find element inside stale root is throwing 'The element reference is stale'");
    expectThrows(StaleElementReferenceException.class, homePage::getContent);

    log("Reload the root to invoke Driver.findElement");
    UtamDevHome homePageReloaded = from(UtamDevHome.class);
    assert homePageReloaded.isPresent();

    log("Call getter to invoke Element.findElement and assign new variable");
    pageContent = homePageReloaded.getContent();
    assert pageContent.isPresent();
  }

  @AfterTest
  public final void tearDown() {
    quitDriver();
  }
}
