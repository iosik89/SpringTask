package com.itk.controller;

import com.itk.model.entity.Department;
import com.itk.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("api/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/{id}")
    public Department findById(@PathVariable UUID id){
        return departmentService.findById(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Department createDepartment(@RequestBody @Valid Department department){
        return departmentService.create(department);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDepartment(@RequestBody UUID id){
        departmentService.delete(id);
    }

}
