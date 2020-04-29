import java.util.Collection;

public interface CollectionSerializer {
    String serialize(Collection<Object> array, String gap, String tab);
}
