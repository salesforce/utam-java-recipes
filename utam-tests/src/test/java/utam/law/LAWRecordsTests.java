package utam.law;

import org.testng.annotations.Factory;
import org.testng.annotations.Test;
import utam.base.UtamWebTestBase;
import utam.flexipage.pageobjects.Tab2;
import utam.law.LAWDataProvider.CreateData;
import utam.law.LAWDataProvider.Data;
import utam.law.LAWDataProvider.Field;
import utam.law.pageobjects.RecordHome;
import utam.lightning.pageobjects.TabBar;
import utam.lightning.pageobjects.Tabset;
import utam.records.pageobjects.Highlights2;
import utam.records.pageobjects.LwcHighlightsPanel;
import utam.records.pageobjects.LwcRecordLayout;
import utam.records.pageobjects.impl.Highlights2Impl;
import utam.records.pageobjects.impl.LwcRecordLayoutImpl;

public class LAWRecordsTests extends UtamWebTestBase {

  private final Data data;

  @Factory(dataProvider = "law", dataProviderClass = LAWDataProvider.class)
  public LAWRecordsTests(Data data) {
    loader.getConfig().setProfile(data.entityType);
    this.data = data;
  }

  private LwcRecordLayout openNewRecordModal() {
    // todo - navigate to Object home using LPOP1 utilities, same for all records
    /*
    getApp().goDirectlyTo(...);
     */

    // todo - click New, same for all records
    /*
    ObjectHomeDesktop objectHome = from(ObjectHomeDesktop.class);
    ListViewManager listViewManager = objectHome.getListView();
    listViewManager.getHeader().waitForAction("New").click();
     */

    // todo - fill all fields
    /*
    RecordActionWrapper create = from(RecordActionWrapper.class);
    create.waitForDetailsPanelContainer();
    */
    return new LwcRecordLayoutImpl();
  }

  /** set all fields based on Json */
  private void setNewRecordDataAndSave(LwcRecordLayout recordLayout) {
    CreateData createData = data.createData;
    for (Field field : createData.fields) {
      field.setValue(recordLayout);
    }
    // todo - save
    /*
    ActionButton button = create.getActions().getActionButton("Save");
    button.click();
    button.waitForAbsence();
     */
  }

  /** set all fields based on Json */
  private void assertNewRecordDataOnDetailsPanel(RecordHome recordHome) {
    Tabset tabset = recordHome.getTabset();
    TabBar tabBar = tabset.getTabBar();
    if (!"Details".equalsIgnoreCase(tabBar.getActiveTabText())) {
      tabBar.clickTab("Details");
    }
    LwcRecordLayout recordLayout =
        tabset
            .getActiveTabContent(Tab2.class)
            .getDetailPanel()
            .getBaseRecordForm()
            .getRecordLayout();
    CreateData createData = data.createData;
    for (Field field : createData.fields) {
      field.assertDetailsPanelValue(recordLayout);
    }
  }

  /** check all fields in highlights panel */
  private void assertNewRecordDataOnHighlightsPanel(RecordHome recordHome) {
    CreateData data = this.data.createData;
    LwcHighlightsPanel highlightsPanel = recordHome.getHighlights();
    // todo - replace by methods
    Highlights2 highlights2 = new Highlights2Impl();

    /*
    String actualValue = highlights2.getPrimaryFieldText().getText();
    String expectedValue = data.getPrimaryField();
    assert expectedValue.equals(actualValue);
     */

    for (Field field : data.fields) {
      field.assertHighlightsValue(highlights2);
    }
  }

  /**
   * https://codesearch.data.sfdc.net/source/xref/app_main_core/app/main/core/
   * ui-force-components/test/func/java/src/one/desktop/record/lwcflexipage/LwcFlexipageRecordCreateUiTestUTAM.java,
   * method testCreateSaveAndNew
   */
  @Test
  public void test() {
    LwcRecordLayout recordLayout = openNewRecordModal();

    // set all fields and save
    setNewRecordDataAndSave(recordLayout);

    RecordHome recordHome = from(RecordHome.class);

    // check all highlights fields
    assertNewRecordDataOnHighlightsPanel(recordHome);

    // navigate to details and check fields, uses same data as was generated
    assertNewRecordDataOnDetailsPanel(recordHome);
  }
}
