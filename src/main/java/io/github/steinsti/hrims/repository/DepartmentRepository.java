package io.github.steinsti.hrims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.github.steinsti.hrims.model.Departments;


@Repository
public interface DepartmentRepository  extends JpaRepository<Departments, Integer>{
    boolean existsByDepartmentName(String departmentName);
    boolean existsByDepartmentCode(String departmentCode);
    
}