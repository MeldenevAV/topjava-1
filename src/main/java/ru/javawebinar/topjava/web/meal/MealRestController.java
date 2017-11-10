package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;
    @Autowired
    private UserService userService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public List<MealWithExceed> getAll() throws NotFoundException {
        log.info("getAll");
        return MealsUtil.getFilteredWithExceeded(service.getAll(AuthorizedUser.id())
                , LocalTime.MIN
                , LocalTime.MAX
                , AuthorizedUser.getCaloriesPerDay());
    }

    public List<MealWithExceed> getAll(LocalDate startDate, LocalDate endDate) throws NotFoundException {
        log.info("getAll");
        return MealsUtil.getFilteredWithExceeded(service.getAll(AuthorizedUser.id(), startDate, endDate)
                , LocalTime.MIN
                , LocalTime.MAX
                , AuthorizedUser.getCaloriesPerDay());
    }

    public Meal get(int id, int userId) throws NotFoundException {
        log.info("get {}", id);
        return service.get(id, userId);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal);
    }

    public void delete(int id, int userId) throws NotFoundException {
        log.info("delete {}", id);
        service.delete(id, userId);
    }

    public void update(Meal meal, int id, int userId) throws NotFoundException {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal, userId);
    }

}