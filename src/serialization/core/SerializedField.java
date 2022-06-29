package serialization.core;

import java.util.Objects;

public class SerializedField {

    private String fieldName;
    private String key;
    private int position;
    private Class<?> clazz;
    private Class<?> declaringClass;
    private String stringValue;
    private byte[] data;

    public String getFieldName() {
        return fieldName;
    }

    public String getKey() {
        return key;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Class<?> getDeclaringClass() {
        return declaringClass;
    }

    public String getStringValue() {
        return stringValue;
    }

    public byte[] getData() {
        return data;
    }

    public int getPosition() {
        return position;
    }

    public static class Builder {
        private SerializedField newSerializedField;

        public Builder() {
            newSerializedField = new SerializedField();
        }

        public Builder setFieldName(String fieldName) {
            newSerializedField.fieldName = fieldName;
            return this;
        }

        public Builder setKey(String key) {
            newSerializedField.key = key;
            return this;
        }

        public Builder setClass(Class<?> clazz) {
            newSerializedField.clazz = clazz;
            return this;
        }

        public Builder setDeclaringClass(Class<?> declaringClass) {
            newSerializedField.declaringClass = declaringClass;
            return this;
        }

        public Builder setStringValue(String stringValue) {
            newSerializedField.stringValue = stringValue;
            return this;
        }

        public Builder setData(byte[] data) {
            newSerializedField.data = data;
            return this;
        }

        public Builder setPosition(int position) {
            newSerializedField.position = position;
            return this;
        }

        public SerializedField build() {
            return newSerializedField;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SerializedField that = (SerializedField) o;
        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
