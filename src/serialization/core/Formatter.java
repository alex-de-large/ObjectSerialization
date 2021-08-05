package serialization.core;

import java.util.List;

public interface Formatter {

    byte[] format(List<SerializedField> serializedFields);
}
