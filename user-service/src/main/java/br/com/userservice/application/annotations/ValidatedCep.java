package br.com.userservice.application.annotations;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;


@Pattern(regexp = "^(\\d{5}-\\d{3})*$", message = "The field Cep shoud be in this format *****-***")
@Documented
@Target({ElementType.FIELD})
@Constraint(validatedBy = { })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatedCep {

    String message() default "The field Cep shoud be in this format *****-***";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
