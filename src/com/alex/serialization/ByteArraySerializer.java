package com.alex.serialization;

import com.alex.serialization.core.Serializer;

public class ByteArraySerializer implements Serializer<byte[]> {

    @Override
    public byte[] serialize(byte[] o) {
        return o;
    }
}
