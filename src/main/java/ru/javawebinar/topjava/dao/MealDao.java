package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealDao {
    Meal updateMeal(Meal meal);

    Meal addMeal(LocalDateTime dateTime, String description, int calories);

    void delete(int id);

    Meal getMealById(int id);

    List<Meal> getAllMeals();
}
