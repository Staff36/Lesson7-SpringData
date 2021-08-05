package ru.tronin.springdata.models.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.tronin.springdata.models.entities.users.Role;
import ru.tronin.springdata.models.entities.users.User;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class UserDto {
    String login;
    String password;
    List<Role> roles;


    public UserDto(User user) {
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.roles = user.getRoles();
    }
}
