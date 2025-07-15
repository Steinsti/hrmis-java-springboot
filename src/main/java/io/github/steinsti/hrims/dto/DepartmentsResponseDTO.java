package io.github.steinsti.hrims.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  DepartmentsResponseDTO {
    private int id;
    private String departmentName;
    private String departmentCode;
    private String description;
    
}