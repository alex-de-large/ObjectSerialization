package com.alex.serialization.core;

public interface Serializer<O> {

    byte[] serialize(O o);
}
