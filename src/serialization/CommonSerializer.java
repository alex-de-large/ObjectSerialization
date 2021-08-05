package serialization;

import serialization.core.Serializer;
import serialization.exceptions.SerializationException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class CommonSerializer implements Serializer<Object> {

    private HashMap<Class<?>, Serializer> serializerHashMap;
    private Options options;

    public CommonSerializer() {
        this(new Options());
    }

    public CommonSerializer(Options options) {
        this.options = options;
        serializerHashMap = new HashMap<>();
        initSerializers();
    }

    public static class Options {
        public Charset stringEncoding;
        // TODO Add options for serializers

        public Options() {
            stringEncoding = StandardCharsets.US_ASCII;
        }
    }

    private void initSerializers() {
        serializerHashMap.put(int.class, new IntegerSerializer());
        serializerHashMap.put(Integer.class,  serializerHashMap.get(int.class));
        serializerHashMap.put(float.class, new FloatSerializer());
        serializerHashMap.put(Float.class,  serializerHashMap.get(float.class));
        serializerHashMap.put(double.class, new DoubleSerializer());
        serializerHashMap.put(Double.class,  serializerHashMap.get(double.class));
        serializerHashMap.put(byte.class, new ByteSerializer());
        serializerHashMap.put(Byte.class, serializerHashMap.get(byte.class));
        serializerHashMap.put(boolean.class, new BooleanSerializer());
        serializerHashMap.put(Boolean.class,  serializerHashMap.get(boolean.class));
        serializerHashMap.put(String.class, new StringSerializer(options.stringEncoding));
        serializerHashMap.put(byte[].class, new ByteArraySerializer());
    }

    @Override
    public byte[] serialize(Object o) {
        if (serializerHashMap.containsKey(o.getClass())) {
            return serializerHashMap.get(o.getClass()).serialize(o);
        } else {
            throw new SerializationException("Can`t serialize " + o.getClass().getSimpleName() + ". No such serializer.");
        }
    }

    public boolean canSerialize(Class<?> clazz) {
        return serializerHashMap.containsKey(clazz);
    }

    public void setSerializer(Class<?> clazz, Serializer serializer) {
        serializerHashMap.put(clazz, serializer);
    }


}
