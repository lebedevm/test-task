package de.comparus.testtask.service;

import de.comparus.testtask.dto.UserDto;
import de.comparus.testtask.jdbcrepository.UserRepository;
import de.comparus.testtask.model.QueryCondition;
import de.comparus.testtask.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;
    @Spy
    List<String> dbNames;

    @Test
    void getUsers() {
        given(dbNames.stream()).willReturn(Stream.of("db-1", "db-2"));
        given(userRepository.selectData(anyString(), anyString(), eq(QueryCondition.BY_USERNAME)))
                .willReturn(List.of(new User("1", "un", "n", "sn")));

        List<UserDto> userDtos = userService.getUsers("bil");

        assertAll(
                () -> assertThat(userDtos).hasSize(2),
                () -> assertThat(userDtos).first().isEqualTo(new UserDto("1", "un", "n", "sn"))
        );
        verify(userRepository, times(2))
                .selectData(anyString(), anyString(), eq(QueryCondition.BY_USERNAME));
    }

    @Test
    void getUsersById() {
        given(dbNames.stream()).willReturn(Stream.of("111", "222", "333"));
        given(userRepository.selectData(anyString(), anyString(), eq(QueryCondition.BY_ID)))
                .willReturn(List.of(new User("1", "un", "n", "sn")));

        List<UserDto> userDtos = userService.getUsersById("1");

        assertAll(
                () -> assertThat(userDtos).hasSize(3),
                () -> assertThat(userDtos).first().isEqualTo(new UserDto("1", "un", "n", "sn"))
        );
        verify(userRepository, times(3))
                .selectData(anyString(), anyString(), eq(QueryCondition.BY_ID));
    }
}
