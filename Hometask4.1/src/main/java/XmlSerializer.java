import java.util.ArrayList;
import java.util.Map;

public class XmlSerializer implements Serializer {
    private final String tab = "\t";

    public String serialize(Object o) {
        try {
            Map<String, Object> fields = Parser.fields(o);
            String xml = serializeMap(fields, "", o.getClass().getName());
            return xml;
        }
        catch (Exception e) {
            System.out.println("Check map format");
            throw new RuntimeException(e);
        }
    }

    private String serializeMap(Map<String, Object> map, String gap, String name) {
        if (map.size() == 1) {
            String field = map.keySet().iterator().next();
            return formSimpleTypeField(field, map, gap, name);
        }
        String json = "<".concat(name).concat(">\n");
        String end = new StringBuilder().append(gap).append("</").append(name).append(">").toString();
        for (String field : map.keySet()) {
            json = formMapField(map, new StringBuilder().append(gap).append(tab).toString(), json, field);
        }
        return json.concat(end);
    }

    private String formSimpleTypeField(String field, Map<String, Object> map, String gap, String name) {
        if (field.equals("primitive")) {
            return formPrimitiveField(map, name);
        } else if (field.equals("array")) {
            return formArrayField(map, gap, name);
        }
        return null;
    }

    private String formPrimitiveField(Map<String, Object> map, String field) {
        return new StringBuilder().append("<")
                .append(field)
                .append(">")
                .append(map.get("primitive").toString())
                .append("</")
                .append(field)
                .append(">\n").toString();
    }

    private String formArrayField(Map<String, Object> map, String gap, String field) {
        return ("\n" + gap).concat("<").concat(field).concat(">\n").concat(gap + tab)
                .concat(serializeArray((ArrayList<Object>) map.get("array"), gap + tab))
                .concat("</")
                .concat(field)
                .concat(">\n");
    }

    private String formMapField(Map<String, Object> map, String gap, String json, String field) {
        return json.concat(gap)
                .concat(serializeMap((Map<String, Object>) map.get(field), gap, field));
    }

    private String serializeArray(ArrayList<Object> array, String gap) {
        String json = "";
        Integer number = 0;
        for (Object o : array) {
            json = json
                    .concat(serializeMap((Map<String, Object>) o, gap, (++number).toString()))
                    .concat(gap);
        }
        return json.substring(0, json.length() - tab.length());
    }
}
