import java.util.Map;

public interface MapSerializer {
    String serialize(Map<String, Object> map, String gap, String tab);
}
