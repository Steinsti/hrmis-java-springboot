package io.github.steinsti.hrims.service;

import io.github.steinsti.hrims.dto.EmployeeRequestDTO;
import io.github.steinsti.hrims.dto.EmployeeResponseDTO;
import io.github.steinsti.hrims.model.Employee;
import io.github.steinsti.hrims.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;


    public List<EmployeeResponseDTO> getAllEmployees(){
        return employeeRepository.findAll().stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    public Optional<EmployeeRequestDTO> getEmployeeById(Integer id) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    EmployeeRequestDTO dto = new EmployeeRequestDTO();
                    dto.setFirstName(employee.getFirstName());
                    dto.setLastName(employee.getLastName());
                    dto.setEmail(employee.getEmail());
                    dto.setPhoneNumber(employee.getPhoneNumber());
                    dto.setHireDate(employee.getHireDate());
                    dto.setDesignation(employee.getDesignation());
                    dto.setDepartment(employee.getDepartment());
                    dto.setDateOfBirth(employee.getDateOfBirth());
                    return dto;
                });
    }
    
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO requestDTO) {
        Employee employee = new Employee();
        employee.setFirstName(requestDTO.getFirstName());
        employee.setLastName(requestDTO.getLastName());
        employee.setEmail(requestDTO.getEmail());
        employee.setPhoneNumber(requestDTO.getPhoneNumber());
        employee.setHireDate(requestDTO.getHireDate());
        employee.setDesignation(requestDTO.getDesignation());
        employee.setDepartment(requestDTO.getDepartment());
        employee.setDateOfBirth(requestDTO.getDateOfBirth());

        Employee savedEmployee = employeeRepository.save(employee);
        return convertToResponseDTO(savedEmployee);
    }

    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }

    public long countAllEmployees(){
        return employeeRepository.count();
    }

    private EmployeeResponseDTO convertToResponseDTO(Employee employee) {
        EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();
        responseDTO.setId(Long.valueOf(employee.getId()));
        responseDTO.setFirstName(employee.getFirstName());
        responseDTO.setLastName(employee.getLastName());
        responseDTO.setEmail(employee.getEmail());
        responseDTO.setPhoneNumber(employee.getPhoneNumber());
        responseDTO.setHireDate(employee.getHireDate());
        responseDTO.setDesignation(employee.getDesignation());
        responseDTO.setDepartment(employee.getDepartment());
        responseDTO.setDateOfBirth(employee.getDateOfBirth());
        return responseDTO;
    }
}
