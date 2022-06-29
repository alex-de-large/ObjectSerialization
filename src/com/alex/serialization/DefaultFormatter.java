package com.alex.serialization;

import com.alex.serialization.core.Formatter;
import com.alex.serialization.core.SerializedField;
import com.alex.serialization.exceptions.SerializationException;

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
