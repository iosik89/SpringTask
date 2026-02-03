package com.itk;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itk.controller.EmployeeController;
import com.itk.model.entity.Employee;
import com.itk.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private final UUID employeeId = UUID.randomUUID();

    @Test
    void getFullNameTest() throws Exception {
        when(employeeService.getFullName(employeeId)).thenReturn("Ivan Ivanov");

        mockMvc.perform(get("/api/employees/{id}/fullName", employeeId))
                .andExpect(status().isOk())
                .andExpect(content().string("Ivan Ivanov"));
    }

    @Test
    void getPositionTest() throws Exception {
        when(employeeService.getPosition(employeeId)).thenReturn("Developer");

        mockMvc.perform(get("/api/employees/{id}/position", employeeId))
                .andExpect(status().isOk())
                .andExpect(content().string("Developer"));
    }

    @Test
    void getDeptTest() throws Exception {
        when(employeeService.getDepartmentName(employeeId)).thenReturn("IT Dept");

        mockMvc.perform(get("/api/employees/{id}/departmentName", employeeId))
                .andExpect(status().isOk())
                .andExpect(content().string("IT Dept"));
    }

    @Test
    void createTest() throws Exception {
        Employee employee = Employee.builder().firstName("Ivan").build();
        when(employeeService.create(any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Ivan"));
    }

    @Test
    void updateTest() throws Exception {
        Employee employee = Employee.builder().firstName("Updated").build();
        when(employeeService.update(eq(employeeId), any(Employee.class))).thenReturn(employee);

        mockMvc.perform(put("/api/employees/{id}", employeeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Updated"));
    }

    @Test
    void deleteTest() throws Exception {
        mockMvc.perform(delete("/api/employees/{id}", employeeId))
                .andExpect(status().isNoContent());

        verify(employeeService).delete(employeeId);
    }
}
