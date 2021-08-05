package serialization;

public final class Serdes {

    private ObjectSerializer objectSerializer;
    private ObjectDeserializer objectDeserializer;

    public Serdes() {
        this(new ObjectSerializer.Builder().build(), new ObjectDeserializer());
    }

    public Serdes(ObjectSerializer objectSerializer) {
        this(objectSerializer, new ObjectDeserializer());
    }

    public Serdes(ObjectDeserializer objectDeserializer) {
        this(new ObjectSerializer.Builder().build(), objectDeserializer);
    }

    public Serdes(ObjectSerializer objectSerializer, ObjectDeserializer objectDeserializer) {
        this.objectSerializer = objectSerializer;
        this.objectDeserializer = objectDeserializer;
    }

    public ObjectSerializer serializer() {
        return objectSerializer;
    }

    public ObjectDeserializer deserializer() {
        return objectDeserializer;
    }
}
