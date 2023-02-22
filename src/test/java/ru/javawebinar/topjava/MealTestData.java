package ru.javawebinar.topjava;

import org.junit.Assert;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static final int ADMIN_MEAL_ID = 100010;
    public static final int USER_ID = 100000;
    public static final int ADMIN_ID = 100001;

    public static final Meal adminLanch = new Meal(ADMIN_MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 29, 10, 0), "Админ ланч", 510);
    public static final Meal adminDinner = new Meal(ADMIN_MEAL_ID + 1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Админ ужин", 1500);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 21, 14, 0), "Завтрак чемпиона", 12345);
    }

    public static Meal copy(Meal original) {
        return new Meal(original.getId(), original.getDateTime(), original.getDescription(), original.getCalories());
    }

    public static void assertMatch(Meal actual, Meal expected) {
        Assert.assertEquals(actual.getDateTime(), expected.getDateTime());
        Assert.assertEquals(actual.getDescription(), expected.getDescription());
        Assert.assertEquals(actual.getCalories(), expected.getCalories());
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
