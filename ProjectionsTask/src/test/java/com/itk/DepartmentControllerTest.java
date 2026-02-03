package com.itk;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itk.controller.DepartmentController;
import com.itk.model.entity.Department;
import com.itk.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DepartmentService departmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findById() throws Exception {
        UUID id = UUID.randomUUID();
        Department dept = new Department(id, "Finance");
        when(departmentService.findById(id)).thenReturn(dept);

        mockMvc.perform(get("/api/department/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Finance"))
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void createDepartment() throws Exception {
        Department dept = new Department(null, "HR");
        Department savedDept = new Department(UUID.randomUUID(), "HR");

        when(departmentService.create(any(Department.class))).thenReturn(savedDept);

        mockMvc.perform(post("/api/department/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dept)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("HR"))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void deleteDepartment() throws Exception {
        UUID idToDelete = UUID.randomUUID();

        mockMvc.perform(delete("/api/department/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(idToDelete)))
                .andExpect(status().isNoContent());
    }

}
