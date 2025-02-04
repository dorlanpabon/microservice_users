package com.pragma.powerup.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.powerup.application.dto.UserClientRequest;
import com.pragma.powerup.application.dto.UserEmployeeRequest;
import com.pragma.powerup.application.dto.UserOwnerRequest;
import com.pragma.powerup.application.handler.IUserHandler;
import com.pragma.powerup.infrastructure.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserRestControllerTest {

    @MockBean
    private IUserHandler userHandler;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
    private UserOwnerRequest userOwnerRequest;
    @Mock
    private UserClientRequest userClientRequest;
    @Mock
    private UserEmployeeRequest userEmployeeRequest;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        userOwnerRequest = new UserOwnerRequest();
        userOwnerRequest.setFirstName("John");
        userOwnerRequest.setLastName("Doe");
        userOwnerRequest.setDocumentNumber("123456789");
        userOwnerRequest.setPhone("+1234567893");
        userOwnerRequest.setBirthDate(LocalDate.of(1990, 5, 15));
        userOwnerRequest.setEmail("test@test.com");
        userOwnerRequest.setPassword("securepassword");

        userClientRequest = new UserClientRequest();
        userClientRequest.setFirstName("John");
        userClientRequest.setLastName("Doe");
        userClientRequest.setDocumentNumber("123456789");
        userClientRequest.setPhone("+1234567893");
        userClientRequest.setEmail("test@test.com");
        userClientRequest.setPassword("securepassword");

        userEmployeeRequest = new UserEmployeeRequest();
        userEmployeeRequest.setFirstName("John");
        userEmployeeRequest.setLastName("Doe");
        userEmployeeRequest.setDocumentNumber("123456789");
        userEmployeeRequest.setPhone("+1234567893");
        userEmployeeRequest.setEmail("test@test.com");
        userEmployeeRequest.setPassword("securepassword");

    }

    @Test
    void saveUserOwner_ReturnsCreated() throws Exception {
        String requestBody = objectMapper.writeValueAsString(userOwnerRequest);

        mockMvc.perform(post("/users/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    void saveUserOwner_ReturnsBadRequest_WhenInvalidRequest() throws Exception {
        UserOwnerRequest invalidRequest = new UserOwnerRequest();
        String invalidRequestBody = objectMapper.writeValueAsString(invalidRequest);

        mockMvc.perform(post("/users/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(invalidRequestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testValidateOwner_ReturnsOk() throws Exception {
        doNothing().when(userHandler).validateOwner(123456789L);

        mockMvc.perform(get("/users/validate-owner/123456789")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    @Test
    void testSaveUserEmployee_ReturnsCreated() throws Exception {
        String requestBody = objectMapper.writeValueAsString(userOwnerRequest);

        mockMvc.perform(post("/users/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    void testSaveUserClient_ReturnsCreated() throws Exception {
        doNothing().when(userHandler).saveUserClient(userClientRequest);
        String requestBody = objectMapper.writeValueAsString(userClientRequest);

        mockMvc.perform(post("/users/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    void testSaveUserClient_ReturnsBadRequest_WhenInvalidRequest() throws Exception {
        UserClientRequest invalidRequest = new UserClientRequest();
        String invalidRequestBody = objectMapper.writeValueAsString(invalidRequest);

        mockMvc.perform(post("/users/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(invalidRequestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetPhone_ReturnsOk() throws Exception {
        when(userHandler.getPhone(123456789L)).thenReturn("+1234567893");

        mockMvc.perform(get("/users/phone/123456789")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }
}
