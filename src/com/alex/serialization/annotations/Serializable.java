package com.alex.serialization.annotations;

import com.alex.serialization.CommonSerializer;
import com.alex.serialization.core.Serializer;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Serializable {
    Class<? extends Serializer<?>> serializer() default CommonSerializer.class;
}
