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
import utam.records.pageobjects.Highlights2;
import utam.records.pageobjects.LwcRecordLayout;
import utam.records.pageobjects.RecordLayoutItem;

public class LAWDataProvider {

  private static final String JSON_FILE_RESOURCE_NAME = "utam/law/%s.json";
  private static final String RANDOM = "random";

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
        data.add(entityData);
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

  enum ArgsType {
    date,
    string,
    number;

    Object getEntryValue(Object value) {
      if (this == string) {
        if (RANDOM.equals(value)) {
          return "name" + Math.random();
        }
        return value.toString();
      } else if (this == number) {
        if (RANDOM.equals(value)) {
          return (Integer) 1000 * Math.random() / 100;
        }
        return Integer.valueOf(value.toString());
      } else if (this == date) {
        if (RANDOM.equals(value)) {
          return "01/01/2020";
        }
      }
      return value.toString();
    }
  }

  static class Data {

    final CreateData createData;
    EntityType entityType;

    @JsonCreator
    Data(@JsonProperty("create") CreateData createData) {
      this.createData = createData;
    }

    String createRecordViaAPI() {
      throw new AssertionError("todo");
    }
  }

  static class CreateData {

    final List<Field> fields;

    @JsonCreator
    CreateData(@JsonProperty("fields") List<Field> fields) {
      this.fields = fields;
    }

    String getPrimaryField() {
      Field field = this.fields.stream().filter(Field::isPrimary).findFirst().orElseThrow();
      return field.values[0].toString();
    }
  }

  static class Field {

    private final List<Integer> items;
    private final Object[] values;
    private final RecordField type;
    private final boolean isPrimary;
    private final int highlightsIndex;

    @JsonCreator
    public Field(
        @JsonProperty(value = "highlightsIndex") Integer highlightsIndex,
        @JsonProperty(value = "isPrimary") boolean isPrimary,
        @JsonProperty(value = "itemIndex", required = true) List<Integer> item,
        @JsonProperty(value = "fieldType", required = true) RecordField type,
        @JsonProperty(value = "args", required = true) List<Args> values) {
      this.items = item;
      this.type = type;
      this.values = values.stream().map(arg -> arg.value).toArray();
      this.isPrimary = isPrimary;
      this.highlightsIndex = highlightsIndex == null ? -1 : highlightsIndex;
    }

    boolean isPrimary() {
      return this.isPrimary;
    }

    void setValue(LwcRecordLayout recordLayout) {
      RecordLayoutItem item = recordLayout.getItem(items.get(0), items.get(1), items.get(2));
      type.setValue(item, values);
    }

    void assertDetailsPanelValue(LwcRecordLayout recordLayout) {
      RecordLayoutItem item = recordLayout.getItem(items.get(0), items.get(1), items.get(2));
      Object actual = type.getValue(item);
      assert actual.equals(values[0]);
    }

    void assertHighlightsValue(Highlights2 highlights) {
      if (highlightsIndex >= 0) {
        String actualValue = highlights.getSecondaryFieldText(highlightsIndex);
        String expectedValue = this.values[0].toString();
        assert actualValue.equals(expectedValue);
      }
    }
  }

  static class Args {

    private final Object value;

    @JsonCreator
    public Args(
        @JsonProperty(value = "value", required = true) Object value,
        @JsonProperty(value = "type", required = true) ArgsType type) {
      this.value = type.getEntryValue(value);
    }
  }
}
