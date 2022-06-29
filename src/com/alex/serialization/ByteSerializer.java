package com.alex.serialization;

import com.alex.serialization.core.Serializer;

public class ByteSerializer implements Serializer<Byte> {

    @Override
    public byte[] serialize(Byte o) {
        return new byte[] {o};
    }
}
