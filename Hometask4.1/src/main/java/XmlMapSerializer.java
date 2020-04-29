import java.util.Map;

public class XmlMapSerializer implements MapSerializer {
    private final FieldFormer fieldFormer;

    public XmlMapSerializer(FieldFormer fieldFormer) {
        this.fieldFormer = fieldFormer;
    }

    @Override
    public String serialize(Map<String, Object> map, String gap, String tab) {
        String name = map.keySet().iterator().next();
        map = (Map<String, Object>) map.get(name);
        if (map.size() == 1) {
            String field = map.keySet().iterator().next();
            return fieldFormer.formSimpleTypeField(field + "_" + name, map, gap, tab);
        }
        StringBuilder xml = new StringBuilder("<" + name + ">\n");
        String end = gap + "</" + name + ">";
        for (String field : map.keySet()) {
            Map<String, Object> fieldMap = Map.of(
                    field, map.get(field)
            );
            xml.append(fieldFormer.formMapField(map, gap + tab, serialize(fieldMap, gap + tab, tab), field));
        }
        return xml.toString() + end;
    }
}
