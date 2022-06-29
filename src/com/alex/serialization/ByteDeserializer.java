package com.alex.serialization;

import com.alex.serialization.core.Deserializer;
import com.alex.serialization.exceptions.DeserializationException;

import java.nio.charset.StandardCharsets;

public class ByteDeserializer implements Deserializer<Byte> {
    @Override
    public Byte deserialize(byte[] data) {
        if (data == null)
            throw new DeserializationException("Expected: byte[]. Got: null");
        if (data.length != 1) {
            throw new DeserializationException("Data length must be equal to 1");
        }

        return Byte.parseByte(new String(data, StandardCharsets.US_ASCII));
    }
}
