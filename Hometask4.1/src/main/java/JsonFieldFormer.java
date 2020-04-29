import java.util.ArrayList;
import java.util.Map;

public class JsonFieldFormer implements FieldFormer {
    private final CollectionSerializer collectionSerializer;

    public JsonFieldFormer(CollectionSerializer collectionSerializer) {
        this.collectionSerializer = collectionSerializer;
    }

    @Override
    public String formSimpleTypeField(String field, Map<String, Object> map, String gap, String tab) {
        if (field.equals("primitive")) {
            return formPrimitiveField(map, field);
        } else if (field.equals("array")) {
            return formArrayField(map, gap, tab, field);
        }
        return null;
    }

    @Override
    public String formPrimitiveField(Map<String, Object> map, String field) {
        return "\"" + map.get(field).toString() + "\",\n";
    }

    @Override
    public String formArrayField(Map<String, Object> map, String tab, String gap, String field) {
        return "[\n" + gap + tab +
                collectionSerializer.serialize((ArrayList<Object>) map.get(field), gap + tab, tab)
                + "\n" + gap + "],\n";
    }

    @Override
    public String formMapField(Map<String, Object> map, String gap, String field, String name) {
        return gap + "\"" + name + "\": " + field;
    }
}
