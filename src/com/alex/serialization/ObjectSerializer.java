package com.alex.serialization;

import com.alex.serialization.annotations.*;
import com.alex.serialization.core.Formatter;
import com.alex.serialization.core.SerializedField;
import com.alex.serialization.core.SerializedFieldComparator;
import com.alex.serialization.core.Serializer;
import com.alex.serialization.exceptions.SerializationException;

import java.lang.reflect.Field;
import java.util.*;

// TODO add null value serialization possibility
// TODO type (field) serializer and formatter select by annotation
public final class ObjectSerializer implements Serializer<Object> {

    private CommonSerializer commonSerializer;
    private com.alex.serialization.core.Formatter defaultFormatter;
    private SerializedFieldComparator serializedFieldComparator;
    private HashMap<Class<?>, com.alex.serialization.core.Formatter> formatters;

    private ObjectSerializer(Builder builder) {
        this.commonSerializer = builder.commonSerializer != null? builder.commonSerializer : new CommonSerializer();
        this.defaultFormatter = builder.defaultFormatter != null? builder.defaultFormatter : new DefaultFormatter();
        this.serializedFieldComparator = builder.serializedFieldComparator != null? builder.serializedFieldComparator :
                new DefaultComparator();
        this.formatters = builder.formatters;
    }

    public static class Builder {

        private CommonSerializer commonSerializer;
        private com.alex.serialization.core.Formatter defaultFormatter;
        private SerializedFieldComparator serializedFieldComparator;
        private HashMap<Class<?>, com.alex.serialization.core.Formatter> formatters;

        public Builder() {
            this.formatters = new HashMap<>();
        }

        public Builder setCommonSerializer(CommonSerializer commonSerializer) {
            this.commonSerializer = commonSerializer;
            return this;
        }

        public Builder setDefaultFormatter(com.alex.serialization.core.Formatter defaultFormatter) {
            this.defaultFormatter = defaultFormatter;
            return this;
        }

        public Builder setFormatter(Class<?> clazz, com.alex.serialization.core.Formatter formatter) {
            formatters.put(clazz, formatter);
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

    public void setCommonSerializer(CommonSerializer commonSerializer) {
        this.commonSerializer = commonSerializer;
    }

    public void setDefaultFormatter(com.alex.serialization.core.Formatter defaultFormatter) {
        this.defaultFormatter = defaultFormatter;
    }

    public void setSerializedFieldComparator(SerializedFieldComparator serializedFieldComparator) {
        this.serializedFieldComparator = serializedFieldComparator;
    }

    public void setFormatter(Class<?> clazz, Formatter formatter) {
        formatters.put(clazz, formatter);
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

        Class<?> sClazz = clazz;
        while (true) {
            sClazz = sClazz.getSuperclass();
            if (sClazz.isAnnotationPresent(Serializable.class)) {
                classes.add(0, sClazz);
            } else {
                break;
            }
        }


        LinkedHashSet<SerializedField> sf = new LinkedHashSet<>();
        for (Class<?> c: classes) {
            for (Field field : c.getDeclaredFields()) {
                field.setAccessible(true);
                if (!ignore(field)) {
                    byte[] data;
                    if (commonSerializer.canSerialize(field.getType())) {
                        data = commonSerializer.serialize(field.get(object));
                    } else {
                        data = serialize(field.get(object));
                    }

                    sf.add(new SerializedField.Builder()
                            .setFieldName(field.getName())
                            .setKey(getKey(field))
                            .setPosition(getPosition(field))
                            .setClass(field.getType())
                            .setDeclaringClass(field.getDeclaringClass())
                            .setStringValue(getStringValue(object, field, data))
                            .setData(data)
                            .build()
                    );
                }
            }
        }

        List<SerializedField> serializedFields = new ArrayList<>(sf);
        if (serializedFieldComparator != null) {
            serializedFields.sort(serializedFieldComparator);
        }
        serializedFields.sort(new ByPosition());

        if (formatters.containsKey(clazz)) {
            return formatters.get(clazz).format(clazz, serializedFields);
        } else {
            return defaultFormatter.format(clazz, serializedFields);
        }
    }

    private String getStringValue(Object object, Field field, byte[] fieldData) throws IllegalAccessException {
        SerializedFieldStringValueExtractionPolicy policy = SerializedFieldStringValueExtractionPolicy.FROM_VALUE;
        if (field.isAnnotationPresent(StringValueExtractionPolicy.class)) {
            policy = field.getAnnotation(StringValueExtractionPolicy.class).policy();
        }
        if (policy == SerializedFieldStringValueExtractionPolicy.FROM_VALUE) {
            return field.get(object).toString();
        } else if (policy == SerializedFieldStringValueExtractionPolicy.FROM_SERIALIZED_DATA) {
            return new String(fieldData);
        } else {
            return "";
        }
    }

    private String getKey(Field field) {
        if (field.isAnnotationPresent(Key.class)) {
            String value = field.getAnnotation(Key.class).value();
            return value.isEmpty() ? field.getName() : value;
        }
        return field.getName();
    }

    private int getPosition(Field field) {
        if (field.isAnnotationPresent(Position.class)) {
            return field.getAnnotation(Position.class).position();
        }
        return Integer.MAX_VALUE;
    }

    private boolean ignore(Field field) {
        return field.isAnnotationPresent(Ignore.class);
    }

    private <T> T createInstance(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        return clazz.newInstance();
    }

    private static class ByPosition implements Comparator<SerializedField> {

        @Override
        public int compare(SerializedField o1, SerializedField o2) {
            return o1.getPosition() - o2.getPosition();
        }
    }
}

