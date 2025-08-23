package io.github.steinsti.hrims.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.steinsti.hrims.dto.DepartmentsRequestDTO;
import io.github.steinsti.hrims.model.Departments;
import io.github.steinsti.hrims.services.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/departments")
@PreAuthorize("hasRole('ADMIN')")
public class DepartmentsController {

    private final DepartmentService departmentService;

    @PostMapping("/save")
    public String saveDepartment(
            @ModelAttribute("departmentRequestDTO") @Valid DepartmentsRequestDTO departmentRequestDTO,
            org.springframework.validation.BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/dashboard/departments/new";
        }
        Departments department = new Departments();
        department.setDepartmentName(departmentRequestDTO.getDepartmentName());
        department.setDepartmentCode(departmentRequestDTO.getDepartmentCode());
        department.setDescription(departmentRequestDTO.getDescription());
        departmentService.save(department);
        return "redirect:/admin/dashboard/departments";
    }
}
