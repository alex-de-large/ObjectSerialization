package serialization;

import serialization.annotations.Parsable;
import serialization.core.Parser;
import serialization.core.SerializedField;
import serialization.exceptions.DeserializationException;

import java.lang.reflect.Field;
import java.util.List;

public final class ObjectDeserializer {

    private CommonDeserializer commonDeserializer;

    public ObjectDeserializer() {
        this(new CommonDeserializer());
    }

    public ObjectDeserializer(CommonDeserializer commonDeserializer) {
        this.commonDeserializer = commonDeserializer;
    }

    public <T> T deserialize(byte[] data, Class<T> clazz) {
        checkIsDeserializable(clazz);
        checkConstructorAccessibility(clazz);
        List<SerializedField> fields = null;
        try {
            fields = ((Parser) clazz.getAnnotation(Parsable.class).using().newInstance()).parse(data);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return deserializeObject(fields, clazz);
    }

    private <T> T deserializeObject(List<SerializedField> fields, Class<T> clazz) {
        T instance = null;
        try {
            instance = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        for (SerializedField sf: fields) {
            try {
                if (sf.getData().length == 0) {
                    continue;
                }
                Field field = clazz.getDeclaredField(sf.getFieldName());
                field.setAccessible(true);
                field.set(instance, commonDeserializer.deserialize(sf.getData(), sf.getClazz()));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
                throw new DeserializationException("Can`t deserialize field '" + sf.getFieldName() + "' in class "
                        + clazz.getSimpleName() + ". No such field. ");
            }
        }
        return instance;
    }

    private void checkConstructorAccessibility(Class<?> clazz) {
        try {
            clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new DeserializationException("Can`t deserialize " + clazz.getSimpleName() + ". Can`t instantiate class.");
        }
    }

    private void checkIsDeserializable(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Parsable.class)) {
            throw new DeserializationException("Can`t deserialize " + clazz.getSimpleName() + " Parsable annotation required.");
        }
    }
}
