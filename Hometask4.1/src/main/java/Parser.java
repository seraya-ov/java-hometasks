import java.lang.reflect.Field;
import java.util.*;

public class Parser {
    private boolean isPrimitiveOrPrimitiveWrapperOrString(Class<?> type) {
        return (type.isPrimitive() && type != void.class) ||
                type == Double.class || type == Float.class || type == Long.class ||
                type == Integer.class || type == Short.class || type == Character.class ||
                type == Byte.class || type == Boolean.class || type == String.class;
    }

    public Map<String, Object> fields(Object obj) {
        try {
            Map<String, Object> objectMap = new HashMap<>();
            obj = handleCollection(obj);
            if (isPrimitiveOrPrimitiveWrapperOrString(obj.getClass())) {
                return handlePrimitive(obj, objectMap);
            }
            if (obj.getClass().isArray()) {
                return handleArray((Object[]) obj, objectMap);
            }
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                objectMap.put(field.getName(), fields(field.get(obj)));
            }
            return objectMap;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, Object> handleArray(Object[] obj, Map<String, Object> objectMap) {
        List<Object> innerArray =  new ArrayList<>();
        for (Object item: obj) {
            innerArray.add(fields(item));
        }
        objectMap.put("array", innerArray);
        return objectMap;
    }

    private Map<String, Object> handlePrimitive(Object obj, Map<String, Object> objectMap) {
        objectMap.put("primitive", obj);
        return objectMap;
    }

    private Object handleCollection(Object obj) {
        if (obj instanceof Collection<?>) {
            obj = ((Collection<?>) obj).toArray();
        }
        return obj;
    }
}
