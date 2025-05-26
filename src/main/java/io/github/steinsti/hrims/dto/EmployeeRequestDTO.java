package io.github.steinsti.hrims.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRequestDTO {

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "last name cannot be blank")
    private String lastName;

    @Email(message = "Email maust be valid")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Designation is required")
    private String designation;

    @NotBlank(message = "Department is required")
    private String department;

    @PastOrPresent(message = "Hire date cannot be in the future")
    private LocalDate hireDate;

}
