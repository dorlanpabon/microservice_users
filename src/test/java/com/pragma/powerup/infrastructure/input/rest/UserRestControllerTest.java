package com.pragma.powerup.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.powerup.application.dto.UserOwnerRequest;
import com.pragma.powerup.application.handler.IUserHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserRestController.class)
@AutoConfigureMockMvc(addFilters = false) // Disables security filters, including CSRF
class UserRestControllerTest {

    @MockBean
    private IUserHandler userHandler;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private UserOwnerRequest userOwnerRequest;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UserRestController(userHandler)).build();

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
        Mockito.doNothing().when(userHandler).saveUserOwner(Mockito.any(UserOwnerRequest.class));

        UserOwnerRequest invalidRequest = new UserOwnerRequest();
        String invalidRequestBody = objectMapper.writeValueAsString(invalidRequest);


        mockMvc.perform(post("/users/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(invalidRequestBody))
                .andExpect(status().isBadRequest());
    }
}
