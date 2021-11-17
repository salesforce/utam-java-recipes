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
import utam.global.pageobjects.RecordActionWrapper;
import utam.global.pageobjects.RecordHomeFlexipage2;
import utam.records.pageobjects.BaseRecordForm;
import utam.records.pageobjects.LwcHighlightsPanel;
import utam.records.pageobjects.LwcRecordLayout;
import utam.records.pageobjects.RecordLayoutBaseInput;
import utam.records.pageobjects.RecordLayoutItem;
import utam.utils.salesforce.RecordType;
import utam.utils.salesforce.TestEnvironment;

/**
 * IMPORTANT: Page objects and tests for Salesforce UI are compatible with application version 236.
 * Test environment is private SF sandbox, not available for external users and has DEFAULT org
 * setup
 *
 * @author Salesforce
 * @since 236
 */
public class RecordUpdateTests extends SalesforceWebTestBase {

  final TestEnvironment testEnvironment = getTestEnvironment("na45");

  @BeforeTest
  public void setup() {
    setupChrome();
    login(testEnvironment, "home");
  }

  @Test
  public void testEditAccountRecord() {

    // todo - replace with existing Account Id for the environment
    final String accountRecordId = "001S7000001pSmBIAU";
    final String recordHomeUrl =
        RecordType.Account.getRecordHomeUrl(testEnvironment.getRedirectUrl(), accountRecordId);

    log("Navigate to the Account Record Home by URL: " + recordHomeUrl);
    getDriver().get(recordHomeUrl);

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
    item.getInputField(RecordLayoutBaseInput.class).getInput().setText(accountName);

    log("Save updated record");
    recordForm.clickFooterButton("Save");
    recordFormModal.waitForAbsence();
  }

  @AfterTest
  public final void tearDown() {
    quitDriver();
  }
}
