package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryMealDaoImpl implements MealDao {
    List<Meal> mealList;

    public InMemoryMealDaoImpl() {
        mealList = MealsUtil.getMealList();
    }

    @Override
    public Meal getById(int id) {
        //TODO переделать фильтр на поиск по циклу
        List<Meal> mealWithId = mealList.stream().filter(meal -> meal.getId() == id).collect(Collectors.toList());
        return mealWithId == null ? null : mealWithId.get(0);
    }

    @Override
    public void update(Meal meal) {
        Meal mealWithId = getById(meal.getId());
        if (meal == null)
            return;
    }

    @Override
    public void add(Meal meal) {
        mealList.add(meal);
    }

    @Override
    public void delete(int id) {
        Meal meal = getById(id);
        if (meal == null)
            return;
        mealList.remove(meal);
    }

    @Override
    public List<MealWithExceed> getAll() {
        return MealsUtil.getFilteredWithExceeded(mealList, LocalTime.MIN, LocalTime.MAX, 2000);
    }
}
