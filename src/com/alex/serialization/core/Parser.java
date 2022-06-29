package serialization.core;

import java.util.List;

public interface Parser {

    List<SerializedField> parse(byte[] data);
}
