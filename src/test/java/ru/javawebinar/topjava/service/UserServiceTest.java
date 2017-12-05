package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collections;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

public class UserServiceTest extends ServiceTest {

    @Autowired
    protected UserService service;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("users").clear();
    }

    @Test
    public void testCreate() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", 1555, false, Collections.singleton(Role.ROLE_USER));
        User created = service.create(newUser);
        newUser.setId(created.getId());
        assertMatch(service.getAll(), ADMIN, newUser, USER);
    }

    @Test
    public void testDuplicateMailCreate() throws Exception {
        thrown.expect(DataAccessException.class);
        service.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.ROLE_USER));
    }

    @Test
    @Override
    public void testDelete() throws Exception {
        service.delete(USER_ID);
        assertMatch(service.getAll(), ADMIN);
    }

    @Test
    public void testNotFoundDelete() throws Exception {
        thrown.expect(NotFoundException.class);
        service.delete(1);
    }

    @Test
    @Override
    public void testGet() throws Exception {
        User user = service.get(USER_ID);
        assertMatch(user, USER);
    }

    @Test
    @Override
    public void testGetNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.get(1);
    }

    @Test
    public void testGetByEmail() throws Exception {
        User user = service.getByEmail("user@yandex.ru");
        assertMatch(user, USER);
    }

    @Test
    @Override
    public void testUpdate() throws Exception {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setCaloriesPerDay(330);
        service.update(updated);
        assertMatch(service.get(USER_ID), updated);
    }

    @Test
    public void testGetAll() throws Exception {
        List<User> all = service.getAll();
        assertMatch(all, ADMIN, USER);
    }

    @Test
    public void testGetUserWithMeal() {
        assertMatch(service.getUserWithMeals(USER_ID).getMeals(), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

}