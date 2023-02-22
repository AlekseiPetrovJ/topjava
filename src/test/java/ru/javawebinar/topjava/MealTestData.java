package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int ADMIN_MEAL_ID = START_SEQ + 10;

    public static final Meal adminBreakfast = new Meal(ADMIN_MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 29, 10, 0), "Админ завтрак", 520);
    public static final Meal adminLunch = new Meal(ADMIN_MEAL_ID + 1, LocalDateTime.of(2020, Month.JANUARY, 29, 13, 0), "Админ ланч", 510);
    public static final Meal adminDinner = new Meal(ADMIN_MEAL_ID + 2, LocalDateTime.of(2020, Month.JANUARY, 30, 18, 0), "Админ ужин", 1500);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 21, 14, 0), "Завтрак чемпиона", 12345);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveAssertion().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(adminLunch);
        updated.setDateTime(LocalDateTime.of(2022, Month.FEBRUARY, 27, 11, 11));
        updated.setDescription("Изменненный обед админа");
        updated.setCalories(444);
        return updated;
    }
}
