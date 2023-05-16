/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: MIT
 * For full license text, see the LICENSE file in the repo root
 * or https://opensource.org/licenses/MIT
 */
package utam.examples.salesforce.web;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utam.force.pageobjects.ListViewManagerHeader;
import utam.global.pageobjects.ConsoleObjectHome;
import utam.global.pageobjects.RecordActionWrapper;
import utam.global.pageobjects.RecordHomeFlexipage2;
import utam.lightning.pageobjects.BaseCombobox;
import utam.records.pageobjects.BaseRecordForm;
import utam.records.pageobjects.LwcRecordLayout;
import utam.records.pageobjects.RecordLayoutItem;
import utam.utils.salesforce.RecordType;
import utam.utils.salesforce.TestEnvironment;

/**
 * IMPORTANT: Page objects and tests for Salesforce UI are compatible with the application version
 * mentioned in published page objects Test environment is private SF sandbox, not available for
 * external users and has DEFAULT org setup
 *
 * @author Salesforce
 * @since Dec 2021
 */
public class RecordCreationTests extends SalesforceWebTestBase {

  private final TestEnvironment testEnvironment = getTestEnvironment("sandbox44");

  @BeforeTest
  public void setup() {
    setupChrome();
    login(testEnvironment, "home");
  }

  /**
   * navigate to object home via URL and click New
   *
   * @param recordType record type affects navigation url
   */
  private RecordActionWrapper openRecordModal(RecordType recordType) {

    log("Navigate to an Object Home for " + recordType.name());
    getDriver().get(recordType.getObjectHomeUrl(testEnvironment.getRedirectUrl()));

    log("Load Accounts Object Home page");
    ConsoleObjectHome objectHome = from(ConsoleObjectHome.class);
    ListViewManagerHeader listViewHeader = objectHome.getListView().getHeader();

    log("List view header: click button 'New'");
    listViewHeader.waitForAction("New").click();

    log("Load Record Form Modal");
    RecordActionWrapper recordFormModal = from(RecordActionWrapper.class);
    Assert.assertTrue(recordFormModal.isPresent(), "record creation modal did not appear");
    return recordFormModal;
  }

  @Test
  public void testAccountRecordCreation() {
    RecordActionWrapper recordFormModal = openRecordModal(RecordType.Account);

    // todo - depending on org setup, modal might be present, then uncomment next lines
    // log("Load Change Record Type Modal");
    // recordTypeModal = from(RecordActionWrapper.class);
    // log("Change Record Type Modal: click button 'New'");
    // recordTypeModal.waitForChangeRecordFooter().clickButton("Next");

    log("Load Record Form Modal");
    BaseRecordForm recordForm = recordFormModal.getRecordForm();
    LwcRecordLayout recordLayout = recordForm.getRecordLayout();

    log("Access record form item by index");
    RecordLayoutItem item = recordLayout.getItem(1, 2, 1);

    log("Enter account name");
    final String accountName = "Utam";
    item.getTextInput().setText(accountName);

    log("Save new record");
    recordForm.clickFooterButton("Save");
    recordFormModal.waitForAbsence();

    log("Load Accounts Record Home page");
    from(RecordHomeFlexipage2.class);
  }

  @Test
  public void testOpportunityRecordCreation() {
    RecordActionWrapper recordFormModal = openRecordModal(RecordType.Opportunity);
    BaseRecordForm recordForm = recordFormModal.getRecordForm();
    LwcRecordLayout recordLayout = recordForm.getRecordLayout();

    log("Enter 'Close date' as 01/01/2023");
    RecordLayoutItem closeDateItem = recordLayout.getItem(1, 1, 2);
    closeDateItem.getDatepicker().setDateText("01/01/2023");

    log("Pick first option in a 'Stage' combobox");
    RecordLayoutItem stageItem = recordLayout.getItem(1, 2, 2);
    BaseCombobox stageCombobox = stageItem.getStageNamePicklist().getBaseCombobox();
    stageCombobox.expandForDisabledInput();
    stageCombobox.pickItem(2);

    log("Find and pick first account, link it to the opportunity");
    RecordLayoutItem accountLookupItem = recordLayout.getItem(1, 3, 1);
    BaseCombobox accountLookup = accountLookupItem.getLookup().getBaseCombobox();
    accountLookup.expand();
    accountLookup.pickItem(1);

    log("Enter opportunity name");
    RecordLayoutItem nameItem = recordLayout.getItem(1, 2, 1);
    nameItem.getTextInput().setText("Opportunity name");
    log("Save new record");
    recordForm.clickFooterButton("Save");
    recordFormModal.waitForAbsence();

    log("Load Accounts Record Home page");
    from(RecordHomeFlexipage2.class);
  }

  @AfterTest
  public final void tearDown() {
    quitDriver();
  }
}
