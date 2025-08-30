package io.github.steinsti.hrims.controller.admin;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.steinsti.hrims.dto.DepartmentsResponseDTO;
import io.github.steinsti.hrims.dto.EmployeeRequestDTO;
import io.github.steinsti.hrims.dto.EmployeeResponseDTO;
import io.github.steinsti.hrims.services.AdminDashboardService;
import io.github.steinsti.hrims.services.interfaces.DepartmentService;
import io.github.steinsti.hrims.services.interfaces.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final AdminDashboardService adminDashboardService;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        List<EmployeeResponseDTO> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        model.addAttribute("totalEmployees", employeeService.countAllEmployees());

        model.addAttribute("departmentCount", adminDashboardService.getDepartmentCount());
        model.addAttribute("pendingApprovals", adminDashboardService.getPendingApprovals());
        model.addAttribute("upcomingHolidays", adminDashboardService.getUpcomingHolidays());

        model.addAttribute("userRole", adminDashboardService.getCurrentUserRole());
        model.addAttribute("userName", adminDashboardService.getCurrentUserName());
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
        model.addAttribute("employees", employees);
        model.addAttribute("userRole", adminDashboardService.getCurrentUserRole());

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

        model.addAttribute("userRole", adminDashboardService.getCurrentUserRole());
        model.addAttribute("userName", adminDashboardService.getCurrentUserName());

        if ("true".equals(request.getHeader("HX-Request"))) {
            return "departments/departmentbase :: department-base";
        } else {
            return "admin/tabs/departmentsTab";
        }
    }
}
