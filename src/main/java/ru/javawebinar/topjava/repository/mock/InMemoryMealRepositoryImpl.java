package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
    public Meal save(Meal meal, int userId) throws NotFoundException {
        log.info("save {}", meal);
        if (meal.getUserId() != userId)
            throw new NotFoundException("meal belong to another user");
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        log.info("delete {}", id);
        try {
            get(id, userId);
        } catch (NotFoundException exc) {
            throw new NotFoundException(exc.getMessage());
        }
        repository.remove(id);
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        log.info("get {}", id);
        Meal meal = repository.get(id);
        if (meal == null)
            throw new NotFoundException("meal not found");
        if (meal.getUserId() != userId)
            throw new NotFoundException("meal belong to another user");
        return meal;
    }

    @Override
    public Collection<Meal> getAll(int userId) throws NotFoundException {
        log.info("getAll");
        Collection<Meal> result = repository.values().stream().filter(user -> user.getUserId() == userId).collect(Collectors.toList());
        if (result == null)
            throw new NotFoundException("Not found meals for userId =  " + userId);
        return result;
    }

    @Override
    public Collection<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate) throws NotFoundException {
        log.info("getAll");
        Collection<Meal> result = repository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate))
                .collect(Collectors.toList());
        if (result == null)
            throw new NotFoundException("Not found meals for userId =  " + userId);
        return result;
    }


}