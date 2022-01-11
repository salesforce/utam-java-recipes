/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: MIT
 * For full license text, see the LICENSE file in the repo root
 * or https://opensource.org/licenses/MIT
 */
package utam.examples.salesforce.web;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utam.flexipage.pageobjects.RecordHomeTemplateDesktop2;
import utam.flexipage.pageobjects.Tab2;
import utam.global.pageobjects.RecordActionWrapper;
import utam.global.pageobjects.RecordHomeFlexipage2;
import utam.lightning.pageobjects.FormattedName;
import utam.lightning.pageobjects.TabBar;
import utam.lightning.pageobjects.Tabset;
import utam.record.flexipage.pageobjects.RecordPageDecorator;
import utam.records.pageobjects.BaseRecordForm;
import utam.records.pageobjects.LwcDetailPanel;
import utam.records.pageobjects.LwcHighlightsPanel;
import utam.records.pageobjects.LwcRecordLayout;
import utam.records.pageobjects.RecordLayoutEventBroker;
import utam.records.pageobjects.RecordLayoutItem;
import utam.utils.salesforce.RecordType;
import utam.utils.salesforce.TestEnvironment;

/**
 * IMPORTANT: Page objects and tests for Salesforce UI are compatible with application version 236.
 * Test environment is private SF sandbox, not available for external users and has DEFAULT org
 * setup
 *
 * @author Salesforce
 * @since Dec 2021
 */
public class RecordUpdateTests extends SalesforceWebTestBase {

  final TestEnvironment testEnvironment = getTestEnvironment("sandbox");

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
    final String accountRecordId = "001S7000002X6FSIA0";
    gotoRecordHomeByUrl(RecordType.Account, accountRecordId);

    log("Load Accounts Record Home page");
    RecordHomeFlexipage2 recordHome = from(RecordHomeFlexipage2.class);

    log("Access Record Highlights panel");
    LwcHighlightsPanel highlightsPanel = recordHome.getAccountHighlights();

    log("Wait for button 'Edit' and click on it");
    highlightsPanel.getActions().waitForRenderedAction("Edit").clickButton();

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

  private static LwcDetailPanel clickAndGetDetails(Tabset tabset) {
    TabBar tabBar = tabset.getTabBar();
    String activeTabName = tabBar.getActiveTabText();
    if (!"Details".equalsIgnoreCase(activeTabName)) {
      tabBar.clickTab("Details");
    }
    return tabset.getActiveTabContent(Tab2.class).getDetailPanel();
  }

  private static void clickButtonOnDetailsFooter(LwcDetailPanel details, String action) {
    details.getBaseRecordForm()
        .getFooter()
        .getActionsRibbon()
        .waitForRenderedAction(action)
        .getHeadlessAction()
        .getLightningButton()
        .click();
  }

  @Test
  public void testInlineEditContactRecord() {

    final String recordId = "003S7000001vfDXIAY";
    gotoRecordHomeByUrl(RecordType.Contact, recordId);

    RecordHomeFlexipage2 recordHome = from(RecordHomeFlexipage2.class);
    RecordPageDecorator statement0 = recordHome.getDecorator();
    RecordLayoutEventBroker statement1 = statement0.getEventBroker();
    RecordLayoutEventBroker statement2 = statement1.waitForTemplate();
    RecordHomeTemplateDesktop2 statement3 = statement2.getGeneratedTemplate(RecordHomeTemplateDesktop2.class);

    LwcDetailPanel detailPanel = clickAndGetDetails(statement3.getTabset2().getTabset());
    LwcRecordLayout recordLayout = detailPanel.getBaseRecordForm().getRecordLayout();
    RecordLayoutItem nameItem = recordLayout.getItem(1,2,1);

    String text = nameItem.getOutputField(FormattedName.class).getInnerText();
    nameItem.getInlineEditButton().click();
    clickButtonOnDetailsFooter(detailPanel, "Save");
    nameItem.waitForOutputField();
    assert nameItem.getOutputField(FormattedName.class).getInnerText().equals(text);
  }

  @AfterTest
  public final void tearDown() {
    quitDriver();
  }
}
