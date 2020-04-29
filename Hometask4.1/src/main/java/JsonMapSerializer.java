import java.util.Map;

public class JsonMapSerializer implements MapSerializer {
    private final FieldFormer fieldFormer;

    public JsonMapSerializer(FieldFormer fieldFormer) {
        this.fieldFormer = fieldFormer;
    }

    @Override
    public String serialize(Map<String, Object> map, String gap, String tab) {
        if (map.size() == 1) {
            String field = map.keySet().iterator().next();
            return fieldFormer.formSimpleTypeField(field, map, gap, tab);
        }
        String json = "{\n";
        String end = "\n" + gap + "},\n";
        for (String field : map.keySet()) {
            json += fieldFormer.formMapField(map,
                    gap + tab, serialize( (Map<String, Object>)map.get(field), gap + tab, tab),
                    field);
        }
        return json.substring(0, json.length() - 2) + end;
    }
}
