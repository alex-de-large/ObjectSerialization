package example;

import com.alex.serialization.core.Formatter;
import com.alex.serialization.core.SerializedField;

import java.nio.charset.StandardCharsets;
import java.util.List;

class TestFormatter implements Formatter {

    @Override
    public byte[] format(Class<?> clazz, List<SerializedField> serializedFields) {
        StringBuilder sb = new StringBuilder();

        int i = 0;
        int n = serializedFields.size();
        for (SerializedField sf: serializedFields) {
            sb.append(sf.getStringValue());
            i += 1;
            if (i < n) {
                sb.append(',');
            }
        }

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }
}
