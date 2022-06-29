package serialization;

import serialization.core.Serializer;
import serialization.exceptions.SerializationException;

import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class CommonSerializer implements Serializer<Object> {

    private HashMap<Class<?>, Serializer> serializerHashMap;
    private Options options;

    public CommonSerializer() {
        this(new Options.Builder().build());
    }

    public CommonSerializer(Options options) {
        this.options = options;
        serializerHashMap = new HashMap<>();
        initSerializers();
    }

    public static class Options {

        private Charset stringEncoding;
        private ByteOrder byteOrder;


        private Options(Builder builder) {
            stringEncoding = builder.stringEncoding;
            byteOrder = builder.byteOrder;
        }

        public static class Builder {

            private Charset stringEncoding;
            private ByteOrder byteOrder;

            public Builder() {
                stringEncoding = StandardCharsets.UTF_8;
                byteOrder = ByteOrder.BIG_ENDIAN;
            }

            public Builder setCharset(Charset charset) {
                this.stringEncoding = charset;
                return this;
            }

            public Builder setByteOrder(ByteOrder byteOrder) {
                this.byteOrder = byteOrder;
                return this;
            }

            public Options build() {
                return new Options(this);
            }
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

    public <O> void setSerializer(Class<?> clazz, Serializer<O> serializer) {
        serializerHashMap.put(clazz, serializer);
    }


}
