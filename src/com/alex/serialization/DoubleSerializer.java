package serialization;

import serialization.core.Serializer;

public class DoubleSerializer implements Serializer<Double> {
    @Override
    public byte[] serialize(Double o) {
        long bits = Double.doubleToLongBits(0);
        return new byte[] {
                (byte) (bits >>> 56),
                (byte) (bits >>> 48),
                (byte) (bits >>> 40),
                (byte) (bits >>> 32),
                (byte) (bits >>> 24),
                (byte) (bits >>> 16),
                (byte) (bits >>> 8),
                (byte) bits
        };
    }
}
