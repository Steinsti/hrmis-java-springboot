package io.github.steinsti.hrims.controller;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.steinsti.hrims.dto.LeaveRequestDTO;
import io.github.steinsti.hrims.model.Employee;
import io.github.steinsti.hrims.services.LeaveRequestService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/leave")
public class LeaveController {

    private final LeaveRequestService leaveService;

    @GetMapping("/apply")
    public String showApplyForm(Model model) {
        model.addAttribute("leaveRequest", new LeaveRequestDTO());
        return "leave/apply";
    }

    @PostMapping("/apply")
    public String submitLeave(@ModelAttribute LeaveRequestDTO dto, @AuthenticationPrincipal Employee employee) {
        leaveService.applyLeave(dto, employee.getId());
        return "redirect:/leave/my-requests";
    }

    @GetMapping("/my-requests")
    public String myRequests(Model model, @AuthenticationPrincipal Employee employee) {
        model.addAttribute("leaveRequests", leaveService.getEmployeeRequests(employee.getId()));
        return "leave/my_requests";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/approve")
    public String approve(@PathVariable UUID id, @RequestParam String comment) {
        leaveService.approveLeave(id, comment);
        return "redirect:/leave/pending";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/reject")
    public String reject(@PathVariable UUID id, @RequestParam String comment) {
        leaveService.rejectLeave(id, comment);
        return "redirect:/leave/pending";
    }

    @GetMapping("/pending")
    public String pending(Model model) {
        model.addAttribute("pendingRequests", leaveService.getPendingRequests());
        return "leave/pending_requests";
    }
}
