package com.itk.model.repository;

import org.springframework.beans.factory.annotation.Value;

public interface EmployeeProjection {

    /*Имя+Фамилия*/
    @Value("#{target.firstName + ' ' + target.lastName}")
    String getFullName();

    /*Должность сотрудника*/
    String getPosition();

    /*Имя отдела сотрудника*/
    @Value("#{target.department.name}")
    String getDepartmentName();

}
