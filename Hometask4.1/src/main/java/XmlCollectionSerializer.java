import java.util.Collection;
import java.util.Map;

public class XmlCollectionSerializer implements CollectionSerializer {
    @Override
    public String serialize(Collection<Object> array, String gap, String tab) {
        StringBuilder xml = new StringBuilder();
        int number = 0;
        for (Object o : array) {
            Map<String, Object> map = (Map<String, Object>) o;
            String key = map.keySet().iterator().next();
            Object value = map.get(key);
            if (key.equals("primitive")) {
                xml.append("<")
                        .append(++number)
                        .append(">").append(value.toString())
                        .append("</").append(number)
                        .append(">\n")
                        .append(gap);
            } else {
                xml.append("<")
                        .append(++number)
                        .append(">\n")
                        .append(gap)
                        .append(tab)
                        .append(serialize((Collection<Object>) value, gap + tab, tab))
                        .append(tab)
                        .append("</")
                        .append(number)
                        .append(">\n")
                        .append(gap);
            }
        }
        return xml.append(tab).toString().substring(0, xml.length() - gap.length());
    }
}
