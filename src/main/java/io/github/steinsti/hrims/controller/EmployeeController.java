package io.github.steinsti.hrims.controller;

import io.github.steinsti.hrims.dto.EmployeeRequestDTO;
import io.github.steinsti.hrims.dto.EmployeeResponseDTO;
import io.github.steinsti.hrims.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMiN', 'USER')")
    public String listEmployees(Model model){

        List<EmployeeResponseDTO> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "employees/list";
    }

    @PostMapping("/create")
    public String createEmployee(@Valid @ModelAttribute("employeeRequestDTO") EmployeeRequestDTO employeeRequestDTO, BindingResult result, Model model) {

        if(result.hasErrors()){
            // If there are validation errors, return the form fragment again
            // This allows the modal to show errors without closing
            model.addAttribute("employeeRequestDTO", employeeRequestDTO);
            // Since this is for HTMX, we return the fragment itself
            // The modal fragment expects 'employees/form :: employeeForm'
            // We need to decide if the modal wrapper should handle the errors or the form itself.
            // For simplicity, let's assume the form handles its own errors visually.
            model.addAttribute("errorMessage","Please correct the form errors");
            return "employees/form :: employeeForm";
        }

        try {
            employeeService.createEmployee(employeeRequestDTO);
            // After successful creation, fetch the updated list of employees
            List<EmployeeResponseDTO> employees = employeeService.getAllEmployees();
            model.addAttribute("employees", employees);
            // Return the updated employee list fragment to replace the target on the dashboard
            return "employees/list :: employeeListContent";

        } catch (Exception e) {
            // For now, let's just re-throw or log and return a basic error if we don't have proper error display in modal
            throw new RuntimeException("Failed to create employee", e);
        }
    }

    // @PostMapping("/save")
    // public String saveEmployee(@ModelAttribute Employee employee) {
    //     employeeService.saveEmployee(employee);
    //     return "redirect:/employees";
    // }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable UUID id) {
        return "redirect:/employees";
    }    
}
