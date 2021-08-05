package serialization;

import serialization.annotations.Ignore;
import serialization.annotations.Key;
import serialization.annotations.Serializable;
import serialization.core.Formatter;
import serialization.core.SerializedField;
import serialization.core.SerializedFieldComparator;
import serialization.core.Serializer;
import serialization.exceptions.SerializationException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.stream.Collectors;

public final class ObjectSerializer implements Serializer<Object> {

    private CommonSerializer commonSerializer;
    private Formatter formatter;
    private SerializedFieldComparator serializedFieldComparator;

    private ObjectSerializer(Builder builder) {
        this.commonSerializer = builder.commonSerializer != null? builder.commonSerializer : new CommonSerializer();
        this.formatter = builder.formatter != null? builder.formatter : new DefaultFormatter();
        this.serializedFieldComparator = builder.serializedFieldComparator != null? builder.serializedFieldComparator :
                new DefaultComparator();
    }

    public static class Builder {

        private CommonSerializer commonSerializer;
        private Formatter formatter;
        private SerializedFieldComparator serializedFieldComparator;

        public Builder() {

        }

        public Builder setCommonSerializer(CommonSerializer commonSerializer) {
            this.commonSerializer = commonSerializer;
            return this;
        }

        public Builder setFormatter(Formatter formatter) {
            this.formatter = formatter;
            return this;
        }

        public Builder setSerializedFieldComparator(SerializedFieldComparator serializedFieldComparator) {
            this.serializedFieldComparator = serializedFieldComparator;
            return this;
        }

        public ObjectSerializer build() {
            return new ObjectSerializer(this);
        }
    }

    public byte[] serialize(Object o) {
        try {
            checkIfSerializable(o);
            return getSerializedObject(o);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SerializationException(e.getMessage());
        }
    }

    private void checkIfSerializable(Object o) {
        if (Objects.isNull(o)) {
            throw new SerializationException("Can't serialize a null object");
        }

        Class<?> clazz = o.getClass();
        if (!clazz.isAnnotationPresent(Serializable.class)) {
            throw new SerializationException("The class " + clazz.getSimpleName() + " is not annotated with Serializable");
        }
    }

    private byte[] getSerializedObject(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();
        ArrayList<Class<?>> classes = new ArrayList<>();
        classes.add(clazz);

        while (true) {
            clazz = clazz.getSuperclass();
            if (clazz.isAnnotationPresent(Serializable.class)) {
                classes.add(clazz);
            } else {
                break;
            }
        }

        LinkedHashSet<SerializedField> sf = new LinkedHashSet<>();
        for (Class<?> c: classes) {
            for (Field field : c.getDeclaredFields()) {
                field.setAccessible(true);
                if (!field.isAnnotationPresent(Ignore.class)) {
                    byte[] data;
                    if (commonSerializer.canSerialize(field.getType())) {
                        data = commonSerializer.serialize(field.get(object));
                    } else {
                        data = serialize(field.get(object));
                    }

                    sf.add(new SerializedField.Builder()
                            .setFieldName(field.getName())
                            .setKey(field.isAnnotationPresent(Key.class)?getKey(field):field.getName())
                            .setClass(field.getType())
                            .setDeclaringClass(field.getDeclaringClass())
                            .setStringValue(field.get(object).toString())
                            .setData(data)
                            .build()
                    );
                }
            }
        }

        return formatter.format(sf.stream().sorted(serializedFieldComparator).collect(Collectors.toList()));
    }

    private String getKey(Field field) {
        String value = field.getAnnotation(Key.class).value();
        return value.isEmpty() ? field.getName() : value;
    }

}
