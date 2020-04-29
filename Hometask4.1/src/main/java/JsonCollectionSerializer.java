import java.util.Collection;
import java.util.Map;

public class JsonCollectionSerializer implements CollectionSerializer{

    @Override
    public String serialize(Collection<Object> array, String gap, String tab) {
        StringBuilder json = new StringBuilder();
        for (Object o : array) {
            Map<String, Object> map = (Map<String, Object>)o;
            String key = map.keySet().iterator().next();
            Object value = map.get(key);
            if (key.equals("primitive")) {
                json.append("\"").append(value.toString()).append("\",\n").append(gap);
            }
            else {
                json.append("[\n")
                        .append(gap)
                        .append(tab)
                        .append(serialize((Collection<Object>) value, gap + tab, tab))
                        .append("\n")
                        .append(gap)
                        .append("],\n")
                        .append(gap);
            }
        }
        return json.toString().substring(0, json.length() - gap.length() - 2);
    }
}
