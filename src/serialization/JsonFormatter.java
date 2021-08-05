package serialization;

import serialization.core.Formatter;
import serialization.core.SerializedField;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class JsonFormatter implements Formatter {

    @Override
    public byte[] format(List<SerializedField> serializedFields) {
        String d ;
        String v;
        StringBuilder sb = new StringBuilder();

        for (SerializedField sf: serializedFields) {
            if (isNumeric(sf.getClazz())) {
                v = sf.getStringValue();
            } else {
                v = new String(sf.getData(), StandardCharsets.US_ASCII);
            }
            d = isString(sf.getClazz())?"\"":"";
            sb.append("\"" + sf.getKey() + "\":" + d + v + d + ",");
        }
        sb.deleteCharAt(sb.length() - 1);

        return ("{" + sb.toString() + "}").getBytes(StandardCharsets.US_ASCII);
    }

    private boolean isString(Class<?> clazz) {
        return clazz.equals(String.class) || clazz.equals(char.class);
    }

    private boolean isNumeric(Class<?> clazz) {
        return !(clazz.equals(char.class) || clazz.equals(boolean.class)) && (clazz.isPrimitive() || clazz.isAssignableFrom(Number.class));
    }
}
