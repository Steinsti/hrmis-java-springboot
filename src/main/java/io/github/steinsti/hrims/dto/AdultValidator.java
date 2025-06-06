package io.github.steinsti.hrims.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class AdultValidator implements ConstraintValidator<Adult, LocalDate> {

    @Override
    public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext context) {
        if (dateOfBirth == null) {
            return true; // Let @NotNull handle nulls if needed
        }
        return dateOfBirth.plusYears(18).isBefore(LocalDate.now()) || dateOfBirth.plusYears(18).isEqual(LocalDate.now());
    }
}