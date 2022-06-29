package test;

import com.alex.serialization.core.Formatter;
import com.alex.serialization.core.SerializedField;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class TestFormatter implements Formatter {


    @Override
    public byte[] format(Class<?> clazz, List<SerializedField> serializedFields) {
        StringBuilder sb = new StringBuilder();

        for (SerializedField sf: serializedFields) {
            sb.append(sf.getStringValue());
            sb.append(',');
        }

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }
}
