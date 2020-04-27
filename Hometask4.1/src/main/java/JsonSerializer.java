import java.util.ArrayList;
import java.util.Map;

public class JsonSerializer implements Serializer {
    private final String tab = "\t";

    public String serialize(Object o) {
        try {
            Map<String, Object> fields = Parser.fields(o);
            String json = serializeMap(fields, "");
            return json.substring(0, json.length() - 2);
        }
        catch (Exception e) {
            System.out.println("Check map format");
            throw new RuntimeException(e);
        }
    }

    private String serializeMap(Map<String, Object> map, String gap) {
        if (map.size() == 1) {
            String field = map.keySet().iterator().next();
            return formSimpleTypeField(field, map, gap);
        }
        String json = "{\n";
        String end = "\n".concat(gap).concat("},\n");
        for (String field : map.keySet()) {
            json = formMapField(map, gap + tab, json, field);
        }
        return json.substring(0, json.length() - 2).concat(end);
    }

    private String formSimpleTypeField(String field, Map<String, Object> map, String gap) {
        if (field.equals("primitive")) {
            return formPrimitiveField(map, field);
        } else if (field.equals("array")) {
            return formArrayField(map, gap, field);
        }
        return null;
    }

    private String formPrimitiveField(Map<String, Object> map, String field) {
        return map.get(field).toString() + ",\n";
    }

    private String formArrayField(Map<String, Object> map, String gap, String field) {
        return "[\n".concat(gap + tab)
                .concat(serializeArray((ArrayList<Object>) map.get(field), gap + tab))
                .concat("\n")
                .concat(gap)
                .concat("],\n");
    }

    private String formMapField(Map<String, Object> map, String gap, String json, String field) {
        return json.concat(gap)
                .concat(field)
                .concat(": ")
                .concat(serializeMap((Map<String, Object>) map.get(field), gap));
    }

    private String serializeArray(ArrayList<Object> array, String gap) {
        String json = "";
        for (Object o : array) {
            json = json.concat(serializeMap((Map<String, Object>) o, gap)).concat(gap);
        }
        return json.substring(0, json.length() - gap.length() - 1);
    }
}
