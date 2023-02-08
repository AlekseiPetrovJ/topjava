package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoImpl implements MealDao {
    private final Map<Integer, Meal> meals = new ConcurrentHashMap<>();
    private static final AtomicInteger id = new AtomicInteger(0);

    @Override
    public Meal addMeal(LocalDateTime dateTime, String description, int calories) {
        int idCurrent = id.getAndIncrement();
        return meals.put(idCurrent, new Meal(idCurrent, dateTime, description, calories));
    }

    @Override
    public Meal updateMeal(Meal meal) {
        return meals.put(meal.getId(), meal);
    }

    @Override
    public void delete(int mealId) {
        meals.remove(mealId);
    }

    @Override
    public Meal getMealById(int mealId) {
        return meals.get(mealId);
    }

    @Override
    public List<Meal> getAllMeals() {
        return new ArrayList<>(meals.values());
    }
}
