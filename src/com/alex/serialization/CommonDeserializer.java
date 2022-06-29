package com.alex.serialization;

import com.alex.serialization.core.Deserializer;
import com.alex.serialization.exceptions.DeserializationException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class CommonDeserializer {

    private HashMap<Class<?>, Deserializer> deserializerHashMap;
    private Options options;

    public CommonDeserializer() {
        this(new Options());
    }

    public CommonDeserializer(Options options) {
        this.options = options;
        deserializerHashMap = new HashMap<>();
        initDeserializers();
    }

    public static class Options {
        public Charset stringEncoding;
        // TODO Add options for deserializers

        public Options() {
            stringEncoding = StandardCharsets.US_ASCII;
        }
    }

    private void initDeserializers() {
        deserializerHashMap.put(int.class, new IntegerDeserializer());
        deserializerHashMap.put(Integer.class, deserializerHashMap.get(int.class));
        deserializerHashMap.put(float.class, new FloatDeserializer());
        deserializerHashMap.put(Float.class, deserializerHashMap.get(float.class));
        deserializerHashMap.put(double.class, new DoubleDeserializer());
        deserializerHashMap.put(Double.class, deserializerHashMap.get(double.class));
        deserializerHashMap.put(byte.class, new ByteDeserializer());
        deserializerHashMap.put(Byte.class, deserializerHashMap.get(byte.class));
        deserializerHashMap.put(String.class, new StringDeserializer(options.stringEncoding));
        deserializerHashMap.put(byte[].class, new ByteArrayDeserializer());
    }

    public Object deserialize(byte[] data, Class<?> clazz) {
        if (!canDeserialize(clazz)) {
            throw new DeserializationException("Can`t deserialize " + clazz.getSimpleName());
        }
        return deserializerHashMap.get(clazz).deserialize(data);
    }

    public void setDeserializer(Class<?> clazz, Deserializer deserializer) {
        deserializerHashMap.put(clazz, deserializer);
    }

    public boolean canDeserialize(Class<?> clazz) {
        return deserializerHashMap.containsKey(clazz);
    }
}
