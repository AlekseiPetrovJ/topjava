package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MealDaoImpl implements MealDao {
    private static final Map<Integer, Meal> meals = new ConcurrentHashMap<>();


    @Override
    public void addMeal(Meal meal) {
        meals.put(meal.getId(), meal);
    }

    @Override
    public void updateMeal(int id, Meal meal) {
        meals.put(id, meal);
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
