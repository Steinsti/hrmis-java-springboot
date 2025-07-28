package io.github.steinsti.hrims.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentsRequestDTO {

    @NotBlank(message = "Department name cannot be blank")
    private String departmentName;

    @NotBlank(message = "Department Code cannot be blank")
    private String departmentCode;

    private String description;

}
