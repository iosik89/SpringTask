package com.itk.service;

import com.itk.model.entity.Department;
import com.itk.model.repository.DepartmentRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;


    public Department findById(UUID id){
        return departmentRepository.findById(id)
                .orElseThrow(()->new NoSuchElementException("Отдел с "+id+"не найден!"));
    }

    @Transactional
    public Department create(@NotNull Department newDep){
        return departmentRepository.save(newDep);
    }

    @Transactional
    public void delete(UUID department) {
        departmentRepository.deleteById(department);
    }



}
