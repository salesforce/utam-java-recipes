package utam.law;

import utam.lightning.pageobjects.BaseCombobox;
import utam.records.pageobjects.RecordLayoutItem;

enum RecordField {
  datePicker,
  inputName,
  lookup,
  pickList;

  void setValue(RecordLayoutItem item, Object[] value) {
    if (this == datePicker) {
      item.getDatepicker().setDateText((String) value[0]);
    } else if (this == inputName) {
      item.getTextInput().setText((String) value[0]);
    } else if (this == pickList) {
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
    if (this == datePicker) {
      return item.getDatepicker().getDateText();
    } else if (this == inputName) {
      return item.getTextInput().getValueText();
    } else if (this == pickList) {
      // todo
    } else if (this == lookup) {
      // todo
    }
    throw new AssertionError(this.name() + ": getValue not implemented");
  }
}
