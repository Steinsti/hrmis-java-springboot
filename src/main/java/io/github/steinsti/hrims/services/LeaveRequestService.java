package io.github.steinsti.hrims.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import io.github.steinsti.hrims.dto.LeaveRequestDTO;
import io.github.steinsti.hrims.enums.LeaveStatus;
import io.github.steinsti.hrims.enums.LeaveType;
import io.github.steinsti.hrims.model.Employee;
import io.github.steinsti.hrims.model.LeaveRequest;
import io.github.steinsti.hrims.repository.LeaveRequestRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepo;
    private final io.github.steinsti.hrims.repository.EmployeeRepository employeeRepository;

    public LeaveRequest applyLeave(LeaveRequestDTO dto, int employeeId) {
        LeaveRequest request = new LeaveRequest();
        request.setId(UUID.randomUUID());
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + employeeId));
        request.setEmployee(employee);
        try {
            request.setLeaveType(LeaveType.valueOf(dto.getLeaveType()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid leave type: " + dto.getLeaveType());
        }
        request.setLeaveType(LeaveType.valueOf(dto.getLeaveType()));
        request.setStartDate(dto.getStartDate());
        request.setEndDate(dto.getEndDate());
        request.setReason(dto.getReason());
        request.setStatus(LeaveStatus.PENDING);
        request.setRequestDate(LocalDate.now());

        return leaveRequestRepo.save(request);
    }

    public List<LeaveRequest> getEmployeeRequests(int empId) {
        return leaveRequestRepo.findByEmployeeId(empId);
    }

    public void approveLeave(UUID requestId, String managerComment) {
        LeaveRequest req = leaveRequestRepo.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Leave request not found with id: " + requestId));
        req.setStatus(LeaveStatus.APPROVED);
        req.setDecisionDate(LocalDateTime.now());
        req.setManagerComment(managerComment);
        leaveRequestRepo.save(req);
    }

    public void rejectLeave(UUID requestId, String managerComment) {
        LeaveRequest req = leaveRequestRepo.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Leave request not found with id: " + requestId));
        req.setStatus(LeaveStatus.REJECTED);
        req.setDecisionDate(LocalDateTime.now());
        req.setManagerComment(managerComment);
        leaveRequestRepo.save(req);
    }

    public List<LeaveRequest> getPendingRequests() {
        return leaveRequestRepo.findByStatus(LeaveStatus.PENDING);
    }
}
