import java.util.Map;

public class JsonSerializer implements Serializer {
    private final String tab;
    private final Parser parser;
    private final MapSerializer mapSerializer;

    public JsonSerializer(Parser parser, String tab, MapSerializer mapSerializer) {
        this.parser = parser;
        this.tab = tab;
        this.mapSerializer = mapSerializer;
    }

    public String serialize(Object o) {
        try {
            Map<String, Object> fields = parser.fields(o);
            String json = mapSerializer.serialize(fields, "", tab);
            return json.substring(0, json.length() - 2);
        }
        catch (Exception e) {
            System.out.println("Check map format");
            throw new RuntimeException(e);
        }
    }
}
