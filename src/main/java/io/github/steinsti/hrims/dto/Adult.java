package io.github.steinsti.hrims.dto;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AdultValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Adult {
	String message() default "Age must be 18 or older";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
