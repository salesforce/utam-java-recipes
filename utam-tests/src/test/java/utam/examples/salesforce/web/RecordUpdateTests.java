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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    final String accountRecordId = "001S7000001pSmBIAU";
    gotoRecordHomeByUrl(RecordType.Account, accountRecordId);

    log("Load Accounts Record Home page");
    RecordHomeFlexipage2 recordHome = from(RecordHomeFlexipage2.class);

    log("Access Record Highlights panel");
    LwcHighlightsPanel highlightsPanel = recordHome.getHighlights();

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

  @Test
  public void testEditLeadRecord() {

    // todo - replace with existing Lead Id for the environment
    final String leadId = "00QS7000001OXVqMAO";
    gotoRecordHomeByUrl(RecordType.Lead, leadId);

    log("Load Lead Record Home page");
    RecordHomeFlexipage2 recordHome = from(RecordHomeFlexipage2.class);

    log("Access Lead Highlights panel");
    LwcHighlightsPanel highlightsPanel = recordHome.getHighlights();

    log("Wait for button 'Edit' and click on it");
    highlightsPanel.getActions().waitForRenderedAction("Edit").clickButton();

    log("Load Record Form Modal");
    RecordActionWrapper recordFormModal = from(RecordActionWrapper.class);
    BaseRecordForm recordForm = recordFormModal.getRecordForm();
    LwcRecordLayout recordLayout = recordForm.getRecordLayout();

    log("Access record form item by index");
    RecordLayoutItem item = recordLayout.getItem(1, 3, 1);

    log("Enter updated lead company name");
    final String formattedDate = DateFormat
            .getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT)
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
