package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    void updateMeal(int id, Meal meal);

    void addMeal(Meal meal);

    void delete(int id);

    Meal getMealById(int id);

    List<Meal> getAllMeals();
}
