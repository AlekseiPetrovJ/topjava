package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal created = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(created, adminBreakfast);
    }

    @Test
    public void getNoOwner() {
        assertThrows(NotFoundException.class, () -> service.get(ADMIN_MEAL_ID, USER_ID));
    }

    @Test
    public void delete() {
        service.delete(ADMIN_MEAL_ID, ADMIN_ID);
        assertThrows(NotFoundException.class, () -> service.get(ADMIN_MEAL_ID, ADMIN_ID));
    }

    @Test
    public void deleteNoOwner() {
        assertThrows(NotFoundException.class, () -> service.delete(ADMIN_MEAL_ID, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> betweenInclusive = service.getBetweenInclusive(LocalDate.parse("2020-01-29"), LocalDate.parse("2020-01-29"), ADMIN_ID);
        assertMatch(betweenInclusive, Arrays.asList(adminLunch, adminBreakfast));
    }

    @Test
    public void duplicateDateTimeCreate() {
        service.create(MealTestData.getNew(), USER_ID);
        assertThrows(DuplicateKeyException.class, () -> service.create(MealTestData.getNew(), USER_ID));
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(ADMIN_ID);
        assertMatch(all, Arrays.asList(adminDinner, adminLunch, adminBreakfast));
    }

    @Test
    public void update() {
        Meal updateMeal = MealTestData.getUpdated();
        service.update(updateMeal, ADMIN_ID);
        assertMatch(service.get(updateMeal.getId(), ADMIN_ID), MealTestData.getUpdated());
    }

    @Test
    public void updateNoOwner() {
        Meal updateMeal = MealTestData.getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(updateMeal, USER_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(MealTestData.getNew(), USER_ID);
        Meal newMeal = MealTestData.getNew();
        int newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }
}