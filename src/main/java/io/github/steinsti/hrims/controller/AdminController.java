package io.github.steinsti.hrims.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.steinsti.hrims.dto.DepartmentsRequestDTO;
import io.github.steinsti.hrims.dto.DepartmentsResponseDTO;
import io.github.steinsti.hrims.dto.EmployeeRequestDTO;
import io.github.steinsti.hrims.dto.EmployeeResponseDTO;
import io.github.steinsti.hrims.service.DepartmentService;
import io.github.steinsti.hrims.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        List<EmployeeResponseDTO> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);

        model.addAttribute("totalEmployees", employeeService.countAllEmployees());

        //Add methods to EmployeeService or other services for departmentCount, pendingApprovals, upcomingHolidays
        model.addAttribute("departmentCount", 15); // Placeholder
        model.addAttribute("pendingApprovals", 8); // Placeholder
        model.addAttribute("upcomingHolidays", 3); // Placeholder

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .findFirst().map(Object::toString).orElse("UNKNOWN");

        model.addAttribute("userRole", userRole);
        model.addAttribute("userName", userName);
        return "admin/dashboard";
    }

    @GetMapping("/employees/new/modal")
    public String showAddEmployeeModal(Model model) {
        model.addAttribute("employeeRequestDTO", new EmployeeRequestDTO());
        return "fragments/modals/employee-form-modal :: employeeFormModal";
    }

    @GetMapping("/dashboard/employees")
    public String getEmployeeBasePage(Model model, HttpServletRequest request) {
        List<EmployeeResponseDTO> employees = employeeService.getAllEmployees();
        String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .findFirst().map(Object::toString).orElse("UNKNOWN");

        model.addAttribute("employees", employees);
        model.addAttribute("userRole", userRole);

        // check if request is from HTMX
        if ("true".equals(request.getHeader("HX-Request"))) {
            return "employees/base :: employeeBaseContent";
        } else {
            return "admin/tabs/employeesTab";
        }
    }

    @GetMapping("/dashboard/departments")
    public String getDepartments(Model model, HttpServletRequest request) {

        List<DepartmentsResponseDTO> departments = departmentService.getDepartments();
        model.addAttribute("departments", departments);

        String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .findFirst().map(Object::toString).orElse("UNKNOWN");
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("userRole", userRole);
        model.addAttribute("userName", userName);

        if ("true".equals(request.getHeader("HX-Request"))) {
            return "departments/departmentbase :: department-base";
        } else {
            return "admin/tabs/departmentsTab";
        }
    }

    @GetMapping("/dashboard/departments/new")
    public String getAddDepartmentForm(Model model) {
        model.addAttribute("departmentRequestDTO", new DepartmentsRequestDTO());

        return "fragments/modals/add-department-modal";
    }

}
