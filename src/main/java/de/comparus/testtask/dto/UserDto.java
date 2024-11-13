package de.comparus.testtask.dto;

import de.comparus.testtask.model.User;

public record UserDto(
        String id,
        String username,
        String name,
        String surname
) {

    public static UserDto fromUser(User user) {
        return new UserDto(user.id(), user.username(), user.name(), user.surname());
    }
}
