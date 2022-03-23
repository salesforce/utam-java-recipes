package utam.examples.low;

import utam.flexipage.pageobjects.Tab2;
import utam.global.pageobjects.RecordHomeFlexipage2;
import utam.lightning.pageobjects.TabBar;
import utam.lightning.pageobjects.Tabset;
import utam.records.pageobjects.LwcDetailPanel;
import utam.records.pageobjects.LwcHighlightsPanel;

public enum EntityType {
  Account,
  Asset,
  Case,
  Contact,
  Lead,
  Opportunity;

  private static LwcDetailPanel clickAndGetDetails(Tabset tabset) {
    TabBar tabBar = tabset.getTabBar();
    String activeTabName = tabBar.getActiveTabText();
    if (!"Details".equalsIgnoreCase(activeTabName)) {
      tabBar.clickTab("Details");
    }
    return tabset.getActiveTabContent(Tab2.class).getDetailPanel();
  }

  Tabset getTabset(RecordHomeFlexipage2 recordHome) {
    if (this == Case) {
      // in core UtamTestUtils.getCaseDetailsPanel()
    }
    // default
    return recordHome.getDecorator().getTemplateDesktop2().getTabset2().getTabset();
  }

  BaseEntityTestingUtil getUtils() {
    throw new AssertionError("todo");
  }

  LwcHighlightsPanel getHighlights(RecordHomeFlexipage2 recordHome) {
    if (this == Lead) {
      return recordHome.getDecorator().getWithSubheaderTemplateDesktop2().getHighlights();
    }
    // this is default template
    // in core UtamTestUtils.getHighlightsPanel(recordHome)
    return recordHome.getDecorator().getTemplateDesktop2().getHighlights();
  }

  public LwcDetailPanel getDetails(RecordHomeFlexipage2 recordHome) {
    Tabset tabset = getTabset(recordHome);
    return clickAndGetDetails(tabset);
  }
}
