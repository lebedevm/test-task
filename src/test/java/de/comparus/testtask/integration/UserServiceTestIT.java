package de.comparus.testtask.integration;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import de.comparus.testtask.dto.UserDto;
import de.comparus.testtask.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Testcontainers
class UserServiceTestIT {

    @Container
    @ServiceConnection
    static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:latest")
            .withInitScript("init-mysql.sql")
            .withUsername("mysqluser")
            .withPassword("mysqlpass")
            .withDatabaseName("database2")
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                    new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(3307), new ExposedPort(3306)))
            ));

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withInitScript("init-postgres.sql")
            .withUsername("postgresuser")
            .withPassword("postgrespass")
            .withDatabaseName("database1")
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                    new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(5433), new ExposedPort(5432)))
            ));

    @Autowired
    private UserService userService;

    @Test
    void getUsers_noUsername() {
        List<UserDto> users = userService.getUsers("");
        assertThat(users).hasSize(26);
    }

    @Test
    void getUsers() {
        List<UserDto> users = userService.getUsers("a");
        assertAll(
                () -> assertThat(users).hasSize(11),
                () -> assertThat(users).map(UserDto::username).allMatch(name -> name.contains("a"))
        );
    }

    @Test
    void getUsersById_numeric() {
        List<UserDto> users = userService.getUsersById("12");
        assertAll(
                () -> assertThat(users).hasSize(2),
                () -> assertThat(users).map(UserDto::name).containsExactly("James", "Lucas")
        );
    }

    @Test
    void getUsersById_text() {
        List<UserDto> users = userService.getUsersById("damiani");
        assertAll(
                () -> assertThat(users).hasSize(1),
                () -> assertThat(users).map(UserDto::name).containsExactly("Vlad")
        );
    }
}
