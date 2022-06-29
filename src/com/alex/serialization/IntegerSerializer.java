package serialization;

import serialization.core.Serializer;

public class IntegerSerializer implements Serializer<Integer> {

    @Override
    public byte[] serialize(Integer o) {
        return new byte[] {
                (byte) (o >>> 24),
                (byte) (o >>> 16),
                (byte) (o >>> 8),
                o.byteValue()
        };
    }

}
