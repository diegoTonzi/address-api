package br.com.cep.api.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;

/**
 * The interface Cep.
 * @author Diego Costa (diegotonzi@gmail.com)
 */
@Pattern(regexp="[0-9]{8}")
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {})
@ReportAsSingleViolation
public @interface Cep {

  String message() default "invalid CEP";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };
 
}