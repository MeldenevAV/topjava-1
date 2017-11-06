package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.util.List;

public interface MealDao {
    public int getNextId();

    public void update(Meal meal);

    public void add(Meal meal);

    public void delete(int id);

    public List<MealWithExceed> getAll();

    public Meal getById(int id);

}
