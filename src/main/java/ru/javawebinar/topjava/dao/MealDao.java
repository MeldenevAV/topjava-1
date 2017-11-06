package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.util.List;

public interface MealDao {
    int getNextId();

    void update(Meal meal);

    void add(Meal meal);

    void delete(int id);

    List<MealWithExceed> getAll();

    Meal getById(int id);

}
