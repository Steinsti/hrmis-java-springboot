package io.github.steinsti.hrims.controller;

import io.github.steinsti.hrims.dto.EmployeeRequestDTO;
import io.github.steinsti.hrims.dto.EmployeeResponseDTO;
import io.github.steinsti.hrims.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;




class AdminControllerTest {

    private EmployeeService employeeService;
    private Model model;
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        employeeService = mock(EmployeeService.class);
        model = mock(Model.class);
        adminController = new AdminController(employeeService);

        // Set up a mock authentication context
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(
                "adminUser",
                "password",
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
            )
        );
    }

    @Test
    void adminDashboard_shouldAddAttributesAndReturnDashboardView() {
        List<EmployeeResponseDTO> employees = Arrays.asList(
            mock(EmployeeResponseDTO.class),
            mock(EmployeeResponseDTO.class)
        );
        when(employeeService.getAllEmployees()).thenReturn(employees);
        when(employeeService.countAllEmployees()).thenReturn(2L);

        String view = adminController.adminDashboard(model);

        assertEquals("admin/dashboard", view);
        verify(model).addAttribute("employees", employees);
        verify(model).addAttribute("totalEmployees", 2L);
        verify(model).addAttribute("departmentCount", 15);
        verify(model).addAttribute("pendingApprovals", 8);
        verify(model).addAttribute("upcomingHolidays", 3);
        verify(model).addAttribute(eq("userRole"), any());
        verify(model).addAttribute("userName", "adminUser");
    }

    @Test
    void showAddEmployeeModal_shouldAddEmployeeRequestDTOAndReturnModalFragment() {
        ArgumentCaptor<EmployeeRequestDTO> captor = ArgumentCaptor.forClass(EmployeeRequestDTO.class);

        String view = adminController.showAddEmployeeModal(model);

        assertEquals("fragments/modals/employee-form-modal :: employeeFormModal", view);
        verify(model).addAttribute(eq("employeeRequestDTO"), captor.capture());
        assertNotNull(captor.getValue());
    }
}

