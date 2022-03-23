package utam.examples.low.fields;

import static utam.examples.low.EntityType.Account;
import static utam.examples.low.EntityType.Lead;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.testng.annotations.Test;
import utam.base.UtamWebTestBase;
import utam.examples.low.Entities;
import utam.examples.low.EntityType;
import utam.force.pageobjects.ListViewManagerHeader;
import utam.global.pageobjects.ConsoleObjectHome;
import utam.global.pageobjects.RecordActionWrapper;
import utam.global.pageobjects.RecordHomeFlexipage2;
import utam.records.pageobjects.BaseRecordForm;
import utam.records.pageobjects.LwcDetailPanel;
import utam.records.pageobjects.LwcHighlightsPanel;
import utam.records.pageobjects.LwcRecordLayout;
import utam.records.pageobjects.RecordLayoutItem;

@Entities({Account, Lead})
public class RecordFormTests extends UtamWebTestBase {

  // initialized in runtime
  private EntityType entityType;
  private Map<RecordField, Object> recordFieldsValues = new HashMap<>();

  /**
   * https://codesearch.data.sfdc.net/source/xref/app_main_core/app/main/core/
   * ui-force-components/test/func/java/src/one/desktop/record/lwcflexipage/LwcFlexipageRecordLwcCreateUiTestUTAM.java
   */
  @Test
  public void testCreateSaveAndNew() {
    // todo - navigate to object home via url
    ConsoleObjectHome objectHome = from(ConsoleObjectHome.class);
    ListViewManagerHeader listViewHeader = objectHome.getListView().getHeader();
    listViewHeader.waitForAction("New").click();
    RecordActionWrapper recordFormModal = from(RecordActionWrapper.class);
    assert recordFormModal.isPresent();
    BaseRecordForm recordForm = recordFormModal.getRecordForm();
    LwcRecordLayout recordLayout = recordForm.getRecordLayout();

    // todo - fill in mandatory fields and save
    RecordLayoutItem item = RecordField.Name.getItem(entityType, recordLayout);
    recordForm.clickFooterButton("Save");
    recordFormModal.waitForAbsence();

    RecordHomeFlexipage2 recordHome = from(RecordHomeFlexipage2.class);
    LwcDetailPanel detailPanel = entityType.getDetails(recordHome);
    recordLayout = detailPanel.getBaseRecordForm().getRecordLayout();
    item = RecordField.Name.getItem(entityType, recordLayout);
  }
}
