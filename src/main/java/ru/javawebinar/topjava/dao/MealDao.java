package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.util.List;

public interface MealDao {
    public void update(MealWithExceed mealWithExceed);

    public void add(Meal meal);

    public void delete(MealWithExceed mealWithExceed);

    public List<MealWithExceed> getAll();

    public MealWithExceed getById(int id);
}
