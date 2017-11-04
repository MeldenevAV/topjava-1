package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryDaoImpl implements MealDao {
    List<Meal> mealList;

    InMemoryDaoImpl() {
        mealList = MealsUtil.getMealList();
    }

    private Meal getMealById(int id) {
        //TODO переделать фильтр на поиск по циклу
        List<Meal> mealWithId = mealList.stream().filter(meal -> meal.getId() == id).collect(Collectors.toList());
        return mealWithId == null ? null : mealWithId.get(0);
    }

    @Override
    public void update(MealWithExceed mealWithExceed) {
        Meal meal = getMealById(mealWithExceed.getId());
        if (meal == null)
            return;
        meal.setCalories(mealWithExceed.getCalories());
        meal.setDateTime(mealWithExceed.getDateTime());
        meal.setDescription(mealWithExceed.getDescription());
    }

    @Override
    public void add(Meal meal) {
        mealList.add(meal);
    }

    @Override
    public void delete(MealWithExceed mealWithExceed) {
        Meal meal = getMealById(mealWithExceed.getId());
        if (meal == null)
            return;
        mealList.remove(meal);
    }

    @Override
    public List<MealWithExceed> getAll() {
        return MealsUtil.getFilteredWithExceeded(mealList, LocalTime.MIN, LocalTime.MAX, 2000);
    }

    @Override
    public MealWithExceed getById(int id) {
        List<MealWithExceed> mealWithExceeds = getAll();
        List<MealWithExceed> finded = mealWithExceeds.stream().filter(meal -> meal.getId() == id).collect(Collectors.toList());
        return finded == null ? null : finded.get(0);
    }
}
