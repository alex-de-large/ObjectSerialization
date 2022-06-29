package serialization;

import serialization.core.Serializer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringSerializer implements Serializer<String> {

    private Charset charset;

    public StringSerializer() {
        this(StandardCharsets.US_ASCII);
    }

    public StringSerializer(Charset charset) {
        this.charset = charset;
    }

    @Override
    public byte[] serialize(String o) {
        return o.getBytes(charset);
    }
}
