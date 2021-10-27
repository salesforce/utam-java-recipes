/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: MIT
 * For full license text, see the LICENSE file in the repo root
 * or https://opensource.org/licenses/MIT
 */
package utam.examples.salesforce;

import org.testng.annotations.Test;
import utam.base.SalesforceWebTestBase;
import utam.core.element.BasicElement;
import utam.core.element.Draggable;
import utam.lightning.pageobjects.Input;
import utam.runtimecdp.pageobjects.SegmentWizard;
import utam.runtimecdp.pageobjects.Viewport;
import utam.tests.pageobjects.Appbuilder;
import utam.tests.pageobjects.Appbuilder.AccordionElement;

public class DragAndDropTestExample extends SalesforceWebTestBase {

  @Test
  public void testCDPSegmentWizard() {
    setupChrome();
    login("cdp", "home");
    getDriver()
        .get(
            "https://jarlsberg01na45.test1.lightning.pc-rnd.force.com/"
                + "lightning/cmp/runtime_cdp__segmentWizardLanding?runtime_cdp__record_id=1sgRN00000001KWYAY");
    SegmentWizard segmentWizard = from(SegmentWizard.class);
    Input input = segmentWizard.getInput();
    String searchFor = "Birth Date";
    input.setText(searchFor);
    Viewport viewer = from(Viewport.class);
    viewer.waitForAttributeWithText(searchFor);
    Draggable draggable = viewer.getFoundAttribute();
    BasicElement target = viewer.getSegmentationCanvas();
    draggable.dragAndDrop(target);
    debug(5);
  }

  @Test
  public void testAppBuilder() {
    setupFirefox(); // chrome does not work!
    login("na45", "home");
    getDriver()
        .get(
            "https://lightningapp.lightning.stmfa.stm.force.com/visualEditor/"
                + "appBuilder.app?&retUrl=https%3A%2F%2Flightningapp.lightning.stmfa.stm.force.com%2Flightning%2Fr%2FCustom_Car__c%2Fa01S7000000Ls5OIAS%2Fview&id=0M0S70000004QMOKA2");
    Appbuilder appbuilder = from(Appbuilder.class);
    debug(10);
    AccordionElement draggable = appbuilder.getAccordion();
    draggable.dragAndDropByOffset(500, -150, 3);
    debug(5);
  }
}
