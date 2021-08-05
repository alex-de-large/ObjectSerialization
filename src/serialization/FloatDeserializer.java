package serialization;

import serialization.core.Deserializer;
import serialization.exceptions.DeserializationException;

public class FloatDeserializer implements Deserializer<Float> {

    @Override
    public Float deserialize(byte[] data) {

        if (data == null)
            throw new DeserializationException("Expected: byte[]. Got: null");
        if (data.length != 4) {
            throw new DeserializationException("Data length must be equal to 4");
        }

        int value = 0;
        for (byte b : data) {
            value <<= 8;
            value |= b & 0xFF;
        }
        return Float.intBitsToFloat(value);
    }
}
