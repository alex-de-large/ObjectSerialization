package com.alex.serialization.core;

import java.util.Comparator;

public interface SerializedFieldComparator extends Comparator<SerializedField> {
    @Override
    int compare(SerializedField o1, SerializedField o2);
}
