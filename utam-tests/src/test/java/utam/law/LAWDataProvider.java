package utam.law;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.DataProvider;
import utam.core.framework.context.Profile;
import utam.law.pageobjects.RecordLayout;
import utam.records.pageobjects.RecordLayoutItem;

public class LAWDataProvider {

  private static final String JSON_FILE_RESOURCE_NAME = "utam/law/%s.json";

  private static URL getJsonDataUrl(EntityType entityType) {
    String fileName = String.format(JSON_FILE_RESOURCE_NAME, entityType.getValue());
    return LAWDataProvider.class.getClassLoader().getResource(fileName);
  }

  private static Data readJsonData(URL url) {
    try {
      return new ObjectMapper().readValue(url, Data.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @DataProvider(name = "law")
  public static Object[] dataProvider() {
    List<Data> data = new ArrayList<>();
    for (EntityType entityType : EntityType.values()) {
      URL url = getJsonDataUrl(entityType);
      if (url != null) {
        Data entityData = readJsonData(url);
        entityData.entityType = entityType;
        data.add(readJsonData(url));
      }
    }
    return data.toArray();
  }

  enum EntityType implements Profile {
    account,
    contact,
    lead,
    opportunity;

    @Override
    public String getName() {
      return "entity";
    }

    @Override
    public String getValue() {
      return name().toLowerCase();
    }
  }

  enum FieldType {
    datepicker,
    inputName;

    void setField(RecordLayoutItem item, String value) {
      if (this == datepicker) {
        item.getDatepicker().setDateText(value);
      } else if (this == inputName) {
        item.getTextInput().setText(value);
      }
      throw new AssertionError("todo");
    }
  }

  static class Data {

    EntityType entityType;
    final List<Field> fields;

    @JsonCreator
    Data(@JsonProperty("fields") List<Field> fields) {
      this.fields = fields;
    }

    String createRecordViaAPI() {
      throw new AssertionError("todo");
    }
  }

  /*
  "fields" : [
      {
        "item" : [ 1, 1, 1 ],
        "value" : "01/01/2020",
        "type" : "datepicker"
      }
   */
  static class Field {

    private final List<Integer> items;
    private final String value;
    private final FieldType type;

    @JsonCreator
    public Field(
        @JsonProperty("item") List<Integer> item,
        @JsonProperty("value") String value,
        @JsonProperty("type") FieldType type) {
      this.items = item;
      this.value = value;
      this.type = type;
    }

    void setValue(RecordLayout recordLayout) {
      RecordLayoutItem item = recordLayout.getItem(items.get(0), items.get(1), items.get(2));
      type.setField(item, value);
    }
  }
}
