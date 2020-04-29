import java.util.ArrayList;
import java.util.Map;

public class XmlFieldFormer implements FieldFormer {
    private final CollectionSerializer collectionSerializer;

    public XmlFieldFormer(CollectionSerializer collectionSerializer) {
        this.collectionSerializer = collectionSerializer;
    }

    @Override
    public String formSimpleTypeField(String field, Map<String, Object> map, String gap, String tab) {
        String[] fieldAndName = field.split("_");
        field = fieldAndName[0];
        String name = fieldAndName[1];
        if (field.equals("primitive")) {
            return formPrimitiveField(map, name);
        } else if (field.equals("array")) {
            return formArrayField(map, tab, gap, name);
        }
        return null;
    }

    @Override
    public String formPrimitiveField(Map<String, Object> map, String field) {
        return "<" +
                field +
                ">" +
                map.get("primitive").toString() +
                "</" +
                field +
                ">\n";
    }

    @Override
    public String formArrayField(Map<String, Object> map, String tab, String gap, String field) {
        return "\n" + gap + "<" + field + ">\n" + gap + tab
                + collectionSerializer.serialize((ArrayList<Object>) map.get("array"), gap + tab, tab)
                + "</"
                + field
                + ">\n";
    }

    @Override
    public String formMapField(Map<String, Object> map, String gap, String field, String name) {
        return gap + field;
    }
}
