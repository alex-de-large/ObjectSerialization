package serialization.core;

public interface Deserializer<O> {

    O deserialize(byte[] data);
}
