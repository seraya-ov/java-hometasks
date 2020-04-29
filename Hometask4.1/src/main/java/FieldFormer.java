import java.util.Map;

public interface FieldFormer {
    String formSimpleTypeField(String field, Map<String, Object> map, String gap, String tab);

    String formPrimitiveField(Map<String, Object> map, String field);

    String formArrayField(Map<String, Object> map, String tab, String gap, String field);

    String formMapField(Map<String, Object> map, String gap, String field, String name);
}
