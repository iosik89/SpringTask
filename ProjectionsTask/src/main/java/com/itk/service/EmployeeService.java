package com.itk.service;

import com.itk.model.entity.Employee;
import com.itk.model.repository.EmployeeProjection;
import com.itk.model.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Transactional
    public String getDepartmentName(UUID id) {
        EmployeeProjection projection = employeeRepository.findProjectionById(id);
        return projection.getDepartmentName();
    }

    public String getPosition(UUID id) {
        EmployeeProjection projection = employeeRepository.findProjectionById(id);
        return projection.getPosition();
    }

    public String getFullName(UUID id) {
        EmployeeProjection projection = employeeRepository.findProjectionById(id);
        return projection.getFullName();
    }

    @Transactional
    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee update(UUID id, Employee details) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Сотрудник не найден!"));
        employee.setFirstName(details.getFirstName());
        employee.setLastName(details.getLastName());
        employee.setPosition(details.getPosition());
        employee.setSalary(details.getSalary());
        employee.setDepartment(details.getDepartment());
        return employeeRepository.save(employee);
    }

    @Transactional
    public void delete(UUID id) {
        employeeRepository.deleteById(id);
    }
}

