import java.util.Map;

public class XmlSerializer implements Serializer {
    private final String tab;
    private final Parser parser;
    private final MapSerializer mapSerializer;

    public XmlSerializer(Parser parser, String tab, MapSerializer mapSerializer) {
        this.tab = tab;
        this.parser = parser;
        this.mapSerializer = mapSerializer;
    }

    public String serialize(Object o) {
        try {
            Map<String, Object> fields = parser.fields(o);
            fields = Map.of(
                    o.getClass().getName(), fields
            );
            String xml = mapSerializer.serialize(fields, "", tab);
            return xml;
        }
        catch (Exception e) {
            System.out.println("Check map format");
            throw new RuntimeException(e);
        }
    }
}
