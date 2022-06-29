package com.alex.serialization.core;

public interface Deserializer<O> {

    O deserialize(byte[] data);
}
