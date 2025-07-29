package io.github.steinsti.hrims.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import io.github.steinsti.hrims.repository.DepartmentRepository;

@Service
public class AdminDashboardService {

    private final DepartmentRepository departmentRepository;

    public AdminDashboardService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public int getDepartmentCount() {
        return (int) departmentRepository.count();
    }

    public int getPendingApprovals() {
        // Replace with real logic
        return 8;
    }

    public int getUpcomingHolidays() {
        // Replace with real logic
        return 3;
    }

    public String getCurrentUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? auth.getName() : "UNKNOWN";
    }

    public String getCurrentUserRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            var authorities = auth.getAuthorities();
            if (authorities != null && !authorities.isEmpty()) {
                return authorities.iterator().next().toString();
            }
        }
        return "UNKNOWN";
    }
}
