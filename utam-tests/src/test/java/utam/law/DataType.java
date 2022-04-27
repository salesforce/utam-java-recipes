package utam.law;

import utam.lightning.pageobjects.BaseCombobox;
import utam.records.pageobjects.RecordLayoutItem;

enum DataType {
  date,
  text,
  lookup,
  picklist;

  void setValue(RecordLayoutItem item, Object[] value) {
    if (this == date) {
      item.getDatepicker().setDateText((String) value[0]);
    } else if (this == text) {
      item.getTextInput().setText((String) value[0]);
    } else if (this == picklist) {
      BaseCombobox picklist = item.getStageNamePicklist().getBaseCombobox();
      picklist.expandForDisabledInput();
      picklist.pickItem((Integer) value[0]);
    } else if (this == lookup) {
      BaseCombobox lookup = item.getLookup().getBaseCombobox();
      lookup.expand();
      lookup.pickItem((Integer) value[0]);
    } else {
      throw new AssertionError(this.name() + ": setValue not implemented");
    }
  }

  Object getValue(RecordLayoutItem item) {
    if (this == date) {
      return item.getDatepicker().getDateText();
    } else if (this == text) {
      return item.getTextInput().getValueText();
    } else if (this == picklist) {
      // todo
    } else if (this == lookup) {
      // todo
    }
    throw new AssertionError(this.name() + ": getValue not implemented");
  }
}
