/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: MIT
 * For full license text, see the LICENSE file in the repo root
 * or https://opensource.org/licenses/MIT
 */
package utam.examples.salesforce;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import utam.base.SalesforceWebTestBase;
import utam.examples.TestEnvironmentUtils;
import utam.tests.pageobjects.Appbuilder;
import utam.tests.pageobjects.Appbuilder.AccordionElement;

public class AppBuilderTestExample extends SalesforceWebTestBase {

  @Test
  public void testAppBuilder() {
    setupFirefox(); // dragAndDrop does not work in chrome
    TestEnvironmentUtils testEnvironment = loginToHomePage("na45");
    String appBuilderUrl =
        testEnvironment.getAppBuilderViewUrl("Custom_Car__c", "0M0S70000004QMOKA2");
    getDriver().get(appBuilderUrl);
    Appbuilder appbuilder = from(Appbuilder.class);
    AccordionElement draggable = appbuilder.getAccordion();
    draggable.dragAndDropByOffset(500, -150, 3);
    debug(5);
  }

  @AfterTest
  public final void tearDown() {
    quitDriver();
  }
}
