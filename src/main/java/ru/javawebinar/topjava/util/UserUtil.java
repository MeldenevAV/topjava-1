package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UserUtil {
    public static final List<User> USERS = Arrays.asList(
            new User("Ivanov Ivan", "IvanovIvan@gmail.com", "password", Role.ROLE_USER),
            new User("Petrov Petr", "PetrovPetr@gmail.com", "password", Role.ROLE_USER),
            new User("Sergeev Sergey", "SergeevSergey@gmail.com", "password", Role.ROLE_USER),
            new User("Alekseev Aleksey", "AlekseevAleksey@gmail.com", "password", Role.ROLE_ADMIN, Role.ROLE_USER)
    );
}