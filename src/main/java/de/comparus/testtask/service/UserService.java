package de.comparus.testtask.service;

import de.comparus.testtask.dto.UserDto;
import de.comparus.testtask.jdbcrepository.UserRepository;
import de.comparus.testtask.model.IdType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static de.comparus.testtask.model.QueryCondition.BY_ID;
import static de.comparus.testtask.model.QueryCondition.BY_USERNAME;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final List<String> dbNames;
    private final Set<String> numericDbNames;

    public List<UserDto> getUsers(String username) {
        return dbNames.stream()
                .map(db -> userRepository.selectData(db, username, BY_USERNAME))
                .flatMap(Collection::stream)
                .map(UserDto::fromUser)
                .toList();
    }

    public List<UserDto> getUsersById(String id) {
        var idType = IdType.getIdType(id);
        return dbNames.stream()
                .filter(idType.limit(numericDbNames))
                .map(db -> userRepository.selectData(db, id, BY_ID))
                .flatMap(Collection::stream)
                .map(UserDto::fromUser)
                .toList();
    }
}
