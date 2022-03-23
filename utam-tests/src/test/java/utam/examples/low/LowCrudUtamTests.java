package utam.examples.low;

import static utam.examples.low.EntityType.Account;
import static utam.examples.low.EntityType.Asset;
import static utam.examples.low.EntityType.Case;
import static utam.examples.low.EntityType.Contact;
import static utam.examples.low.EntityType.Lead;
import static utam.examples.low.EntityType.Opportunity;

import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.Test;
import utam.base.UtamWebTestBase;
import utam.examples.low.fields.RecordField;
import utam.force.pageobjects.ListViewManagerHeader;
import utam.global.pageobjects.ConsoleObjectHome;
import utam.global.pageobjects.RecordActionWrapper;
import utam.global.pageobjects.RecordHomeFlexipage2;
import utam.records.pageobjects.BaseRecordForm;
import utam.records.pageobjects.LwcDetailPanel;
import utam.records.pageobjects.LwcHighlightsPanel;
import utam.records.pageobjects.LwcRecordLayout;
import utam.records.pageobjects.RecordLayoutItem;

@Entities({Account, Asset, Lead, Case, Contact, Opportunity })
public class LowCrudUtamTests extends UtamWebTestBase {

  // initialized in runtime
  private EntityType entityType;

  /**
   * https://codesearch.data.sfdc.net/source/xref/app_main_core/app/main/core/
   * ui-force-components/test/func/java/src/one/desktop/record/lwcflexipage/RRHAccountUiTestUTAM.java
   */
  @Test
  public void testHighlights() {
    String recordId = entityType.getUtils().makeEntity();
    // todo - navigate
    RecordHomeFlexipage2 recordHome = from(RecordHomeFlexipage2.class);
    LwcHighlightsPanel highlights = entityType.getHighlights(recordHome);
    assert highlights.isPresent();
  }

  /**
   * https://codesearch.data.sfdc.net/source/xref/app_main_core/app/main/core/
   * ui-force-components/test/func/java/src/one/desktop/record/lwcflexipage/RRHAccountUiTestUTAM.java
   */
  @Test
  public void testDetails() {
    String recordId = entityType.getUtils().makeEntity();
    // todo - navigate
    RecordHomeFlexipage2 recordHome = from(RecordHomeFlexipage2.class);
    LwcDetailPanel details = entityType.getDetails(recordHome);
    assert details.isPresent();
  }
}
