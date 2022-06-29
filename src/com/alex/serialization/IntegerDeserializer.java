package serialization;

import serialization.core.Deserializer;
import serialization.exceptions.DeserializationException;

public class IntegerDeserializer implements Deserializer<Integer> {

    @Override
    public Integer deserialize(byte[] data) {
        if (data == null)
            throw new DeserializationException("Expected: byte[]. Got: null");
        if (data.length != 4) {
            throw new DeserializationException("Data length must be equal to 4");
        }

        int i = 0;
        for (byte b : data) {
            i <<= 8;
            i |= b & 0xFF;
        }
        return i;
    }
}
