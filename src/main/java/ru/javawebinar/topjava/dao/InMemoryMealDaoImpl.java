package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealDaoImpl implements MealDao {
    private Map<Integer, Meal> mealMap;

    public static List<Meal> getMealList() {
        List<Meal> result = new ArrayList<>();
        result.add(new Meal(currentId.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        result.add(new Meal(currentId.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        result.add(new Meal(currentId.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        result.add(new Meal(currentId.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        result.add(new Meal(currentId.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        result.add(new Meal(currentId.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        return result;
    }

    private static Map<Integer, Meal> getMealMap() {
        Map<Integer, Meal> result = new ConcurrentHashMap<>();
        getMealList().forEach(meal -> result.put(meal.getId(), meal));
        return result;
    }

    private static AtomicInteger currentId = new AtomicInteger();

    public InMemoryMealDaoImpl() {
        mealMap = getMealMap();
    }

    @Override
    public Meal getById(int id) {
        return mealMap.get(id);
    }

    @Override
    public int getNextId() {
        return currentId.incrementAndGet();
    }

    @Override
    public void update(Meal meal) {
        mealMap.put(meal.getId(), meal);
    }

    @Override
    public void add(Meal meal) {
        mealMap.put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        mealMap.remove(id);
    }

    @Override
    public List<MealWithExceed> getAll() {
        return MealsUtil.getFilteredWithExceeded(new ArrayList<>(mealMap.values()),
                LocalTime.MIN,
                LocalTime.MAX,
                2000);
    }
}
