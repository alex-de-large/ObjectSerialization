package serialization.annotations;

import serialization.CommonSerializer;
import serialization.core.Serializer;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Serializable {
    Class<? extends Serializer<?>> serializer() default CommonSerializer.class;
}
