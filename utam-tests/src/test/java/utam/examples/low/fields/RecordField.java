package utam.examples.low.fields;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;
import utam.examples.low.EntityType;
import utam.records.pageobjects.LwcRecordLayout;
import utam.records.pageobjects.RecordLayoutItem;

public enum RecordField {
  Name(((entityType, recordLayout) -> recordLayout.getItem(1, 2, 1)));

  private final BiFunction<EntityType, LwcRecordLayout, RecordLayoutItem> getField;

  RecordField(BiFunction<EntityType, LwcRecordLayout, RecordLayoutItem> getField) {
    this.getField = getField;
  }

  public RecordLayoutItem getItem(EntityType entityType, LwcRecordLayout recordLayout) {
    return getField.apply(entityType, recordLayout);
  }

  public static Set<RecordField> getMandatoryFields(EntityType entityType) {
    Set<RecordField> fields = new HashSet<>();
    fields.add(Name);
    if(entityType == EntityType.Opportunity) {
      //fields.
    }
    return fields;
  }
}
