package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealDaoImpl implements MealDao {
    List<Meal> mealList;

    public static List<Meal> getMealList() {
        List<Meal> result = new ArrayList<Meal>();
        result.add(new Meal(currentId.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        result.add(new Meal(currentId.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        result.add(new Meal(currentId.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        result.add(new Meal(currentId.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        result.add(new Meal(currentId.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        result.add(new Meal(currentId.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        return result;
    }

    private static AtomicInteger currentId = new AtomicInteger();

    public InMemoryMealDaoImpl() {
        mealList = getMealList();
    }

    @Override
    public Meal getById(int id) {
        //TODO переделать фильтр на поиск по циклу
        List<Meal> mealWithId = mealList.stream().filter(meal -> meal.getId() == id).collect(Collectors.toList());
        return mealWithId == null ? null : mealWithId.get(0);
    }

    @Override
    public int getNextId() {
        return currentId.incrementAndGet();
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
