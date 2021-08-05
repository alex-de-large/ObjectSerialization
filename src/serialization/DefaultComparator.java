package serialization;

import serialization.core.SerializedField;
import serialization.core.SerializedFieldComparator;

public class DefaultComparator implements SerializedFieldComparator {

    @Override
    public int compare(SerializedField o1, SerializedField o2) {
        if (o1.getDeclaringClass().equals(o2.getDeclaringClass())) {
            return 0;
        }
        if (o1.getDeclaringClass().isAssignableFrom(o2.getDeclaringClass())) {
            return 1;
        }
        return -1;
    }
}
