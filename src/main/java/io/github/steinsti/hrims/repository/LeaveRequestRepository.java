package io.github.steinsti.hrims.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.steinsti.hrims.enums.LeaveStatus;
import io.github.steinsti.hrims.model.LeaveRequest;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, UUID> {

    List<LeaveRequest> findByEmployeeId(int employeeId);

    List<LeaveRequest> findByStatus(LeaveStatus status);

}
