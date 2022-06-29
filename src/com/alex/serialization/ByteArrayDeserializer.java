package com.alex.serialization;

import com.alex.serialization.core.Deserializer;

public class ByteArrayDeserializer implements Deserializer<byte[]> {
    @Override
    public byte[] deserialize(byte[] data) {
        return data;
    }
}
