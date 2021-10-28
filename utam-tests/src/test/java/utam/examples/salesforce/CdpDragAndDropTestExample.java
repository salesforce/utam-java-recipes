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
import utam.lightning.pageobjects.Input;
import utam.runtimecdp.pageobjects.SegmentWizard;
import utam.runtimecdp.pageobjects.Viewport;
import utam.runtimecdp.pageobjects.Viewport.DraggableAttributeElement;
import utam.runtimecdp.pageobjects.Viewport.SegmentationCanvasElement;

public class CdpDragAndDropTestExample extends SalesforceWebTestBase {

  @Test
  public void testCDPSegmentWizard() {
    setupChrome();
    TestEnvironmentUtils testEnvironment = loginToHomePage("cdp");
    String cdpWizardUrl = testEnvironment.getCDPSegmentWizardUrl("1sgRN00000001KWYAY");
    getDriver().get(cdpWizardUrl);
    SegmentWizard segmentWizard = from(SegmentWizard.class);
    Input input = segmentWizard.getBuilder().getAttributeLibrary().waitForInput();
    String searchFor = "Birth Date";
    input.setText(searchFor);
    Viewport viewport = from(Viewport.class);
    viewport.waitForAttributeWithText(searchFor);
    DraggableAttributeElement draggable = viewport.getDraggableAttribute();
    SegmentationCanvasElement target = viewport.getSegmentationCanvas();
    draggable.dragAndDrop(target);
    debug(7);
  }

  @AfterTest
  public final void tearDown() {
    quitDriver();
  }
}
