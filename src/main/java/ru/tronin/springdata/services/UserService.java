package ru.tronin.springdata.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.tronin.springdata.exceptions.NoEntityException;
import ru.tronin.springdata.models.entities.users.Role;
import ru.tronin.springdata.models.entities.users.User;
import ru.tronin.springdata.repositories.RoleRepository;
import ru.tronin.springdata.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        List<Role> roles = List.of(roleRepository.findByName("ROLE_USER"));
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public User findByLogin(String login) {
        User user = userRepository.findUserByLogin(login).orElse(null);
        if (user == null){
            throw new NoEntityException(String.format("User with login %s not found", login));
        }
        Hibernate.initialize(user.getRoles());
        return user;
    }

    public User findByLoginAndPassword(String login, String password) {
        User userFromDB = findByLogin(login);
        if (userFromDB != null) {
            if (passwordEncoder.matches(password, userFromDB.getPassword())) {
                return userFromDB;
            }
        }
        return null;
    }


}
