package ro.zizicu.mservice.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ro.zizicu.mservice.order.BaseIntegrationTest;
import ro.zizicu.mservice.order.entities.Employee;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class EmployeeControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setAddress("test address");
        employee.setFirstName("first-name");
        employee.setLastName("last-name");
        employee.setCity("city_test");
        employee.setBirthDate(new Date());
        employee.setHireDate(new Date());

        String jsonRequest = objectMapper.writeValueAsString(employee);
        MvcResult result  =  mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("first-name"))
                .andExpect(jsonPath("$.lastName").value("last-name"))
                .andExpect(jsonPath("$.city").value("city_test"))
                .andReturn();
    }

    @Test
    void testLoadEmployee() throws Exception {
        MvcResult result  =  mockMvc.perform(get("/employees/{id}", 5))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("5"))
                .andExpect(jsonPath("$.firstName").value("Steven"))
                .andExpect(jsonPath("$.lastName").value("Buchanan"))
                .andExpect(jsonPath("$.title").value("Sales Manager"))
                .andReturn();
    }

}
