package com.alex.serialization.annotations;

import com.alex.serialization.SerializedFieldStringValueExtractionPolicy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface StringValueExtractionPolicy {
    SerializedFieldStringValueExtractionPolicy policy();
}
