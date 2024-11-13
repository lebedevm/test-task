package de.comparus.testtask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.comparus.testtask.dto.UserDto;
import de.comparus.testtask.exception.ControllerExceptionHandler;
import de.comparus.testtask.service.UserService;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    UserService userService;
    ObjectMapper objectMapper = new ObjectMapper();

    @Nested
    class Users {
        LogCaptor infoCaptor = LogCaptor.forClass(UserController.class);
        LogCaptor errorCaptor = LogCaptor.forClass(ControllerExceptionHandler.class);

        @Test
        void getUsers() throws Exception {
            var data = List.of(
                    new UserDto("1", "Kean", "Keanu", "Rivz"),
                    new UserDto("12", "Angel", "Angelina", "Joly"),
                    new UserDto("32", "Mon", "Monica", "Belicci")
            );
            String responseBody = objectMapper.writeValueAsString(data);
            given(userService.getUsers(anyString())).willReturn(data);

            mockMvc.perform(get("/users").param("username", "n"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(content().json(responseBody))
                    .andExpect(jsonPath("$", hasSize(3)))
                    .andExpect(jsonPath("$[0].name", is("Keanu")))
                    .andExpect(jsonPath("$[*].id", containsInRelativeOrder("1", "12", "32")));

            assertThat(infoCaptor.getInfoLogs())
                    .contains("/GET /users username='n'");
        }

        @Test
        void getUsers_wrongUsername() throws Exception {
            mockMvc.perform(get("/users").param("username", ";--"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.messages[0].param", is("username")))
                    .andExpect(jsonPath("$.messages[0].message", is("Wrong parameter")));

            assertThat(errorCaptor.getErrorLogs())
                    .contains("Validation error = 'ConstraintViolationException', path = '', errors: \n" +
                            "ValidationParamError[param=username, message=Wrong parameter]");
        }
    }

    @Nested
    class UsersById {
        LogCaptor infoCaptor = LogCaptor.forClass(UserController.class);
        LogCaptor errorCaptor = LogCaptor.forClass(ControllerExceptionHandler.class);

        @Test
        void getUsersById() throws Exception {
            var data = List.of(
                    new UserDto("1", "Kean", "Keanu", "Rivz"),
                    new UserDto("1", "Mon", "Monica", "B")
            );
            String responseBody = objectMapper.writeValueAsString(data);
            given(userService.getUsersById(anyString())).willReturn(data);

            mockMvc.perform(get("/users/1"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(content().json(responseBody))
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].name", is("Keanu")))
                    .andExpect(jsonPath("$[*].id", contains("1", "1")));

            assertThat(infoCaptor.getInfoLogs())
                    .contains("/GET /users/1");
        }

        @Test
        void getUsersById_wrongId() throws Exception {
            mockMvc.perform(get("/users/--"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.messages[0].param", is("id")))
                    .andExpect(jsonPath("$.messages[0].message", is("Wrong parameter")));

            assertThat(errorCaptor.getErrorLogs())
                    .contains("Validation error = 'ConstraintViolationException', path = '', errors: \n" +
                            "ValidationParamError[param=id, message=Wrong parameter]");
        }
    }
}
