package io.github.steinsti.hrims.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.steinsti.hrims.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

}
