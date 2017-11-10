package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {}", meal);
        if (meal.getUserId() != userId)
            return null;
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(int id, int userId) {
        log.info("delete {}", id);
        Meal meal = get(id, userId);
        if (meal == null)
            return;
        repository.remove(id);
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {}", id);
        Meal meal = repository.get(id);
        if (meal == null || meal.getUserId() != userId)
            return null;
        return meal;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("getAll");
        return repository.values().stream().filter(user -> user.getUserId() == userId).collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getAll");
        repository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate))
                .collect(Collectors.toList());
        return null;
    }


}