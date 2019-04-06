package ru.ilya.zoo.validator.annotations;

import ru.ilya.zoo.validator.ContentTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ContentTypeValidator.class})
public @interface ValidContentType {

    String message() default "Content type not supported. Now you should import only from xml file";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

