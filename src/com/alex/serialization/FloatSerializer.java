package com.alex.serialization;

import com.alex.serialization.core.Serializer;

public class FloatSerializer implements Serializer<Float> {

    @Override
    public byte[] serialize(Float o) {

        long bits = Float.floatToRawIntBits(o);
        return new byte[] {
                (byte) (bits >>> 24),
                (byte) (bits >>> 16),
                (byte) (bits >>> 8),
                (byte) bits
        };
    }
}
