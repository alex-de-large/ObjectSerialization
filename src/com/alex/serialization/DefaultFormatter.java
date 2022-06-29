package serialization;

import serialization.core.Formatter;
import serialization.core.SerializedField;
import serialization.exceptions.SerializationException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class DefaultFormatter implements Formatter {

    @Override
    public byte[] format(Class<?> clazz, List<SerializedField> serializedFields) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (SerializedField sf: serializedFields) {
            try {
                out.write(sf.getData());
            } catch (IOException exception) {
                throw new SerializationException("Formatting error occurred.");
            }
        }
        return out.toByteArray();
    }

}
