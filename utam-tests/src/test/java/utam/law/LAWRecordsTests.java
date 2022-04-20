package utam.law;

import org.testng.annotations.Factory;
import org.testng.annotations.Test;
import utam.base.UtamWebTestBase;
import utam.law.LAWDataProvider.Data;

public class LAWRecordsTests extends UtamWebTestBase {

  private final Data data;

  @Factory(dataProvider = "law", dataProviderClass = LAWDataProvider.class)
  public LAWRecordsTests(Data data) {
    loader.getConfig().setProfile(data.entityType);
    this.data = data;
  }

  /**
   * https://codesearch.data.sfdc.net/source/xref/app_main_core/app/main/core/
   * ui-force-components/test/func/java/src/one/desktop/record/lwcflexipage/RRHAccountUiTestUTAM.java
   */
  @Test
  public void testHighlights() {
    String recordId = data.createRecordViaAPI();
    /*
    UtamTestUtils.navigateToRecordHome(this, getApp(), recordId);
    RecordHomeFlexipage2 recordHome = from(RecordHomeFlexipage2.class);
    LwcHighlightsPanel highlights = lawTestingutil.getHighlights(recordHome);
    assertTrue("Highlights panel is not visible", highlights.getRecordLayout().waitForHighlights2()
        .getSecondaryFieldContent(FormattedText.class).isPresent());
    */
  }

  /**
   * https://codesearch.data.sfdc.net/source/xref/app_main_core/app/main/core/
   * ui-force-components/test/func/java/src/one/desktop/record/lwcflexipage/RRHAccountUiTestUTAM.java
   */
  @Test
  public void testDetails() {
    String recordId = data.createRecordViaAPI();
    /*
    UtamTestUtils.navigateToRecordHome(this, getApp(), recordId);
    RecordHomeFlexipage2 recordHome = from(RecordHomeFlexipage2.class);
    LwcDetailPanel details = lawTestingutil.getDetails(recordHome);
    // assert details.isPresent();
    assertTrue("Details panel is not displayed ", details.getRoot().isVisible());
     */
  }
}
