package com.itk.controller;

import com.itk.model.entity.Employee;
import com.itk.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/{id}/fullName")
    public String getFullName(@PathVariable UUID id) {
        return employeeService.getFullName(id);
    }

    @GetMapping("/{id}/position")
    public String getPosition(@PathVariable UUID id) {
        return employeeService.getPosition(id);
    }

    @GetMapping("/{id}/departmentName")
    public String getDept(@PathVariable UUID id) {
        return employeeService.getDepartmentName(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody @Valid  Employee employee) {
        return employeeService.create(employee);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee update(@PathVariable UUID id, @RequestBody Employee employee) {
        return employeeService.update(id, employee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        employeeService.delete(id);
    }
}
