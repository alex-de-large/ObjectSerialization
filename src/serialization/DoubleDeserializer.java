package serialization;

import serialization.core.Deserializer;
import serialization.exceptions.DeserializationException;

public class DoubleDeserializer implements Deserializer<Double> {
    @Override
    public Double deserialize(byte[] data) {
        if (data == null)
            throw new DeserializationException("Expected: byte[]. Got: null");
        if (data.length != 8) {
            throw new DeserializationException("Data length must be equal to 8");
        }

        long value = 0;
        for (byte b : data) {
            value <<= 8;
            value |= b & 0xFF;
        }
        return Double.longBitsToDouble(value);
    }
}
