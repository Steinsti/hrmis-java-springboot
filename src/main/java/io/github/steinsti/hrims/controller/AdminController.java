package io.github.steinsti.hrims.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
@Controller
public class AdminController {

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model  model){
    
        String userRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
            .findFirst().map(Object::toString).orElse("UNKNOWN");
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("userRole", userRole);
        model.addAttribute("userName", userName);
        return "admin/dashboard";
    }
}
