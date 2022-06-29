package com.alex.serialization;

import com.alex.serialization.core.Serializer;

import java.nio.charset.StandardCharsets;

public class BooleanSerializer implements Serializer<Boolean> {
    @Override
    public byte[] serialize(Boolean o) {
        return o.toString().getBytes(StandardCharsets.US_ASCII);
    }
}
