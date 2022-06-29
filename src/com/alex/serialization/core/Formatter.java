package serialization.core;

import java.util.List;

public interface Formatter {

    byte[] format(Class<?> clazz, List<SerializedField> serializedFields);
}
