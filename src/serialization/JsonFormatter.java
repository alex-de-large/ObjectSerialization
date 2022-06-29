package serialization;

import serialization.core.Formatter;
import serialization.core.SerializedField;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JsonFormatter implements Formatter {

    private Charset charset;

    public JsonFormatter(Charset charset) {
        this.charset = charset;
    }

    @Override
    public byte[] format(Class<?> clazz, List<SerializedField> serializedFields) {
        String d ;
        String v;
        StringBuilder sb = new StringBuilder();

        for (SerializedField sf: serializedFields) {
            if (isNumeric(sf.getClazz())) {
                v = sf.getStringValue();
            } else {
                v = new String(sf.getData(), charset);
            }
            d = isString(sf.getClazz())? "\"" :"";
            sb.append("\"").append(sf.getKey()).append("\":").append(d).append(v).append(d).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);

        return ("{" + sb.toString() + "}").getBytes(charset);
    }

    private boolean isString(Class<?> clazz) {
        return clazz.equals(String.class) || clazz.equals(char.class);
    }

    private boolean isNumeric(Class<?> clazz) {
        return !(clazz.equals(char.class) || clazz.equals(boolean.class)) && (clazz.isPrimitive() || clazz.isAssignableFrom(Number.class));
    }
}
