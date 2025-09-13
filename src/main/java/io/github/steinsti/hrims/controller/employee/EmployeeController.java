package io.github.steinsti.hrims.controller.employee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.steinsti.hrims.dto.EmployeeRequestDTO;
import io.github.steinsti.hrims.dto.EmployeeResponseDTO;
import io.github.steinsti.hrims.services.interfaces.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/dashboard")
    public String employeeDashboard(Model model) {
        model.addAttribute("userName", employeeService.getCurrentUserName());
        model.addAttribute("userRole", employeeService.getCurrentUserRole());
        Optional<EmployeeResponseDTO> employeeOpt = employeeService.getCurrentEmployeeInfo();
        employeeOpt.ifPresent(employee -> model.addAttribute("employee", employee));
        return "employees/employee-dashboard";
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/dashboard-content")
    public String dashboardContent(Model model) {
        Optional<EmployeeResponseDTO> employeeOpt = employeeService.getCurrentEmployeeInfo();
        employeeOpt.ifPresent(employee -> model.addAttribute("employee", employee));
        return "employees/dashboard-content :: dashboardContent(employee=${employee})";
    }

    @PreAuthorize("hasRole('EMPLOYEE', 'HR_ADMIN')")
    @GetMapping("/profile")
    public String employeeProfile(Model model) {
        Optional<EmployeeResponseDTO> employeeOpt = employeeService.getCurrentEmployeeInfo();
        employeeOpt.ifPresent(employee -> model.addAttribute("employee", employee));
        return "employees/profile";
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/leave")
    public String employeeLeave(Model model) {
        Optional<EmployeeResponseDTO> employeeOpt = employeeService.getCurrentEmployeeInfo();
        employeeOpt.ifPresent(employee -> model.addAttribute("employee", employee));
        return "employees/employee-leave-tab";
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN', 'HR_ADMIN')")
    public String listEmployees(Model model) {
        List<EmployeeResponseDTO> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "employees/base :: employeeBaseContent";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR_ADMIN')")
    public String createEmployee(@Valid @ModelAttribute("employeeRequestDTO") EmployeeRequestDTO employeeRequestDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("employeeRequestDTO", employeeRequestDTO);
            model.addAttribute("errorMessage", "Please correct the form errors");
            return "employees/form :: employeeForm";
        }
        try {
            employeeService.createEmployee(employeeRequestDTO);
            List<EmployeeResponseDTO> employees = employeeService.getAllEmployees();
            model.addAttribute("employees", employees);
            return "employees/list :: employeeListContent";
        } catch (Exception e) {
            throw new RuntimeException("Failed to create employee", e);
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable UUID id) {
        return "redirect:/employees";
    }

    // endregion
    // region Future Extensions
    // Add new controller methods here for easier organization.
    // endregion
}
