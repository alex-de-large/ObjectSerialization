package com.alex.serialization;

import com.alex.serialization.core.Deserializer;
import com.alex.serialization.exceptions.DeserializationException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringDeserializer implements Deserializer<String> {

    private Charset charset;

    public StringDeserializer() {
        this(StandardCharsets.US_ASCII);
    }

    public StringDeserializer(Charset charset) {
        this.charset = charset;
    }

    @Override
    public String deserialize(byte[] data) {
        if (data == null) {
            throw new DeserializationException("Expected: byte[]. Got: null");
        }
        return new String(data, charset);
    }
}
