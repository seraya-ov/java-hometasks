package countmap;

import java.util.Map;
import java.util.Set;

public class CountMapImpl<K> implements CountMap<K> {
    private Map<K, Integer> map;

    public CountMapImpl(Map<K, Integer> map) {
        this.map = map;
    }

    private <T extends K> void addSeveralItems(T o, int number) {
        if (map.containsKey(o)) {
            map.put(o, map.get(o) + number);
        }
        else {
            map.put(o, number);
        }
    }

    public Set<K> getKeys() {
        return map.keySet();
    }

    public <T extends K> void add(T o) {
        this.addSeveralItems(o, 1);
    }

    public <T extends K> int getCount(T o) {
        return map.get(o);
    }

    public <T extends K> int remove(T o) {
        return map.remove(o);
    }

    public int size() {
        return map.size();
    }

    public void addAll(CountMap<? super K> source) {
        Set<K> keys = (Set<K>) source.getKeys();
        for (K key: keys) {
            this.addSeveralItems(key, source.getCount(key));
        }
    }

    public Map<? super K, ? super Integer> toMap() {
        return map;
    }

    public void toMap(Map<? extends K, ? extends Integer> destination) {
        destination = map;
    }
}
