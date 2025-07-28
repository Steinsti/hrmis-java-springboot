package io.github.steinsti.hrims.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.github.steinsti.hrims.dto.DepartmentsResponseDTO;
import io.github.steinsti.hrims.model.Departments;
import io.github.steinsti.hrims.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public List<DepartmentsResponseDTO> getDepartments() {
        return departmentRepository.findAll().stream().map(dept -> new DepartmentsResponseDTO(
                dept.getId(),
                dept.getDepartmentName(),
                dept.getDepartmentCode(),
                dept.getDescription()
        )).collect(Collectors.toList());
    }

    public void save(Departments department) {
        departmentRepository.save(department);
    }
}
