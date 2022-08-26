/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: MIT
 * For full license text, see the LICENSE file in the repo root
 * or https://opensource.org/licenses/MIT
 */
package utam.examples.salesforce.web;

import static org.testng.Assert.assertEquals;

import java.text.DateFormat;
import java.util.Calendar;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utam.core.framework.context.StringValueProfile;
import utam.flexipage.pageobjects.Tab2;
import utam.global.pageobjects.RecordActionWrapper;
import utam.global.pageobjects.RecordHomeFlexipage2;
import utam.lightning.pageobjects.Button;
import utam.lightning.pageobjects.TabBar;
import utam.lightning.pageobjects.Tabset;
import utam.records.pageobjects.BaseRecordForm;
import utam.records.pageobjects.LwcDetailPanel;
import utam.records.pageobjects.LwcHighlightsPanel;
import utam.records.pageobjects.LwcRecordLayout;
import utam.records.pageobjects.RecordLayoutItem;
import utam.utils.salesforce.RecordType;
import utam.utils.salesforce.TestEnvironment;

/**
 * IMPORTANT: Page objects and tests for Salesforce UI are compatible with application version
 * mentioned in published page objects. Test environment is private SF sandbox, not available for
 * external users and has DEFAULT org setup
 *
 * @author Salesforce
 * @since Dec 2021
 */
public class RecordUpdateTests extends SalesforceWebTestBase {

  private final TestEnvironment testEnvironment = getTestEnvironment("sandbox44");

  @BeforeTest
  public void setup() {
    setupChrome();
    login(testEnvironment, "home");
  }

  private void gotoRecordHomeByUrl(RecordType recordType, String recordId) {
    String recordHomeUrl = recordType.getRecordHomeUrl(testEnvironment.getRedirectUrl(), recordId);
    log("Navigate to the Record Home by URL: " + recordHomeUrl);
    getDriver().get(recordHomeUrl);
  }

  @Test
  public void testEditAccountRecord() {

    // todo - replace with existing Account Id for the environment
    final String accountRecordId = testEnvironment.getAccountId();
    gotoRecordHomeByUrl(RecordType.Account, accountRecordId);

    log("Load Accounts Record Home page");
    RecordHomeFlexipage2 recordHome = from(RecordHomeFlexipage2.class);

    log("Access Record Highlights panel");
    LwcHighlightsPanel highlightsPanel = recordHome.getHighlights();

    log("Wait for button 'Edit' and click on it");
    highlightsPanel.getActions().getActionRendererWithTitle("Edit").clickButton();

    log("Load Record Form Modal");
    RecordActionWrapper recordFormModal = from(RecordActionWrapper.class);
    BaseRecordForm recordForm = recordFormModal.getRecordForm();
    LwcRecordLayout recordLayout = recordForm.getRecordLayout();

    log("Access record form item by index");
    RecordLayoutItem item = recordLayout.getItem(1, 2, 1);

    log("Enter updated account name");
    final String accountName = "Utam";
    item.getTextInput().setText(accountName);

    log("Save updated record");
    recordForm.clickFooterButton("Save");
    recordFormModal.waitForAbsence();
  }

  @Test
  public void testInlineEditContactRecord() {

    // todo - replace with existing Contact Id for the environment
    final String recordId = testEnvironment.getContactId();
    gotoRecordHomeByUrl(RecordType.Contact, recordId);

    RecordHomeFlexipage2 recordHome = from(RecordHomeFlexipage2.class);
    Tabset tabset = recordHome.getTabset();

    log("Select 'Details' tab");
    TabBar tabBar = tabset.getTabBar();
    String activeTabName = tabBar.getActiveTabText();
    if (!"Details".equalsIgnoreCase(activeTabName)) {
      tabBar.clickTab("Details");
    }
    log("Access Name field on Details panel");
    LwcDetailPanel detailPanel = tabset.getActiveTabContent(Tab2.class).getDetailPanel();
    LwcRecordLayout recordLayout = detailPanel.getBaseRecordForm().getRecordLayout();
    RecordLayoutItem nameItem = recordLayout.getItem(1, 2, 1);

    log("Remember value of the name field");
    String nameString = nameItem.getFormattedName().getInnerText();

    log("Click inline edit (pencil) next to the Name field");
    nameItem.getInlineEditButton().click();

    log("Click Save at the bottom of Details panel");
    Button saveButton = detailPanel
        .getBaseRecordForm()
        .getFooter()
        .getActionsRibbon()
        .getActionRendererWithTitle("Save")
        .getHeadlessAction()
        .getLightningButton();
    saveButton.click();
    saveButton.waitForAbsence();

    log("Wait for field to be updated");
    nameItem.waitForOutputField();
    log("Check that field value has not changed");
    assertEquals(nameItem.getFormattedName().getInnerText(), nameString);
  }

  @Test
  public void testEditLeadRecord() {
    // set profile to lead entity type
    // loader.getConfig().setProfile(new StringValueProfile("entity", "lead"));
    // loader.resetContext();
    setProfile(RecordType.Lead);

    // todo - replace with existing Lead Id for the environment
    final String leadId = testEnvironment.getLeadId();
    gotoRecordHomeByUrl(RecordType.Lead, leadId);

    log("Load Lead Record Home page");
    RecordHomeFlexipage2 recordHome = from(RecordHomeFlexipage2.class);

    log("Access Lead Highlights panel");
    LwcHighlightsPanel highlightsPanel = recordHome.getHighlights();

    log("Wait for button 'Edit' and click on it");
    highlightsPanel.getActions().getActionRendererWithTitle("Edit").clickButton();

    log("Load Record Form Modal");
    RecordActionWrapper recordFormModal = from(RecordActionWrapper.class);
    BaseRecordForm recordForm = recordFormModal.getRecordForm();
    LwcRecordLayout recordLayout = recordForm.getRecordLayout();

    log("Access record form item by index");
    RecordLayoutItem item = recordLayout.getItem(1, 3, 1);

    log("Enter updated lead company name");
    final String formattedDate =
        DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT)
            .format(Calendar.getInstance().getTime());
    final String updatedLeadCompanyName = "Utam and Co. updated on " + formattedDate;
    item.getTextInput().setText(updatedLeadCompanyName);

    log("Save updated record");
    recordForm.clickFooterButton("Save");
    recordFormModal.waitForAbsence();
  }

  @AfterTest
  public final void tearDown() {
    quitDriver();
  }
}
