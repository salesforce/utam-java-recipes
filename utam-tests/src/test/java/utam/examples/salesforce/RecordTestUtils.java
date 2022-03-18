/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: MIT
 * For full license text, see the LICENSE file in the repo root
 * or https://opensource.org/licenses/MIT
 */
package utam.examples.salesforce;

import utam.global.pageobjects.RecordHomeFlexipage2;
import utam.lightning.pageobjects.Datepicker;
import utam.lightning.pageobjects.Input;
import utam.lightning.pageobjects.Tabset;
import utam.records.pageobjects.LwcHighlightsPanel;
import utam.records.pageobjects.RecordLayoutItem;

/**
 * utilities for records testing
 *
 * @author elizaveta.ivanova
 * @since Spring'22
 */
public class RecordTestUtils {

  /**
   * todo - add chain to recordHomeFlexipage2
   * https://github.com/salesforce/utam-java-recipes/blob/main/utam-preview/src/main/resources/spec/one/recordHomeFlexipage2.utam.json#L101
   *
   * @param recordHome root page object
   * @return highlights panel
   */
  public static LwcHighlightsPanel getHighlightsPanel(RecordHomeFlexipage2 recordHome) {
    return recordHome.getDecorator().getTemplateDesktop2().getHighlights();
  }

  /**
   * this method should be used for Lead record type
   *
   * @param recordHome root page object
   * @return highlights panel
   */
  public static LwcHighlightsPanel getHighlightsPanelWithSubheader(
      RecordHomeFlexipage2 recordHome) {
    return recordHome.getDecorator().getWithSubheaderTemplateDesktop2().getHighlights();
  }

  /**
   * todo - add chain to recordHomeFlexipage2
   * https://github.com/salesforce/utam-java-recipes/blob/main/utam-preview/src/main/resources/spec/one/recordHomeFlexipage2.utam.json#L115
   *
   * this method can be used for account, contact, opportunities
   * @param recordHome root page object
   * @return tabset
   */
  public static Tabset getTabset(RecordHomeFlexipage2 recordHome) {
    return recordHome.getDecorator().getTemplateDesktop2().getTabset2().getTabset();
  }

  /**
   * todo - add chain to recordLayoutItem
   * https://github.com/salesforce/utam-java-recipes/blob/main/utam-preview/src/main/resources/spec/records/recordLayoutItem.utam.json#L152
   *
   * @param layoutItem record field
   * @return datepicker
   */
  public static Datepicker getDatepicker(RecordLayoutItem layoutItem) {
    return layoutItem.getInputField(Input.class).getDatepicker();
  }
}
