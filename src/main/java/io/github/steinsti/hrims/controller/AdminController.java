package io.github.steinsti.hrims.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import io.github.steinsti.hrims.dto.EmployeeRequestDTO;
import io.github.steinsti.hrims.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final EmployeeService employeeService;

    @GetMapping("/dashboard")
    public String adminDashboard(Model  model){
    
        model.addAttribute("totalEmployees", employeeService.countAllEmployees());

        //TODO: Add methods to EmployeeService or other services for departmentCount, pendingApprovals, upcomingHolidays
        model.addAttribute("departmentCount", 15); // Placeholder
        model.addAttribute("pendingApprovals", 8); // Placeholder
        model.addAttribute("upcomingHolidays", 3); // Placeholder

        String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
            .findFirst().map(Object::toString).orElse("UNKNOWN");
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("userRole", userRole);
        model.addAttribute("userName", userName);
        return "admin/dashboard";
    }

    @GetMapping("/employees/new/modal")
    public String showAddEmployeeModal(Model model) {
        model.addAttribute("employeeRequestDTO", new EmployeeRequestDTO());
        return "fragments/modals/employee-form-modal :: employeeFormModal";
    }
    
}
