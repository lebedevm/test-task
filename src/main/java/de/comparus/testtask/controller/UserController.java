package de.comparus.testtask.controller;

import de.comparus.testtask.annotation.AllUsersApi;
import de.comparus.testtask.annotation.UsersByIdApi;
import de.comparus.testtask.controller.validation.SqlProtected;
import de.comparus.testtask.dto.UserDto;
import de.comparus.testtask.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "Aggregated users data from all databases")
public class UserController {

    private final UserService userService;

    @AllUsersApi
    @GetMapping
    public List<UserDto> getUsers(@RequestParam(name = "username", required = false, defaultValue = "")
                                  @SqlProtected String username) {
        log.info("/GET /users username='{}'", username);
        return userService.getUsers(username);
    }

    @UsersByIdApi
    @GetMapping("/{id}")
    public List<UserDto> getUsersById(@PathVariable(name = "id")
                                      @SqlProtected String id) {
        log.info("/GET /users/{}", id);
        return userService.getUsersById(id);
    }
}
