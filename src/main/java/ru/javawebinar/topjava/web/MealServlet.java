package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.GeneratorID;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private static final MealDao mealDao = new MealDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");

        //Начальное заполнение данных
        if (mealDao.getAllMeals().size() == 0) {
            MealsUtil.getMeals()
                    .forEach(mealDao::addMeal);
        }

        if (request.getParameter("add") != null && request.getParameter("add").equals("true")) {
            request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
            return;
        } else if (request.getParameter("saveAdd") != null && request.getParameter("saveAdd").equals("true")) {
            String datetime = request.getParameter("datetime");
            String description = request.getParameter("description");
            int calories = Integer.parseInt(request.getParameter("calories"));
            LocalDateTime localDateTime = LocalDateTime.parse(datetime);
            Integer id = null;
            try {
                id = Integer.parseInt(request.getParameter("id"));
            } catch (NumberFormatException e) {
            }
            Meal addedMeal;
            if (id == null) {
                addedMeal = new Meal(GeneratorID.getId(), localDateTime, description, calories);
                mealDao.addMeal(addedMeal);
                log.debug("added meal" + addedMeal);

            } else {
                addedMeal = new Meal(id, localDateTime, description, calories);
                mealDao.updateMeal(id, addedMeal);
                log.debug("edit meal" + addedMeal);
            }

        } else if (request.getParameter("edit") != null && request.getParameter("edit").equals("edit")) {
            Meal editMeal = mealDao.getMealById(Integer.parseInt(request.getParameter("id")));
            log.debug("edit meal" + editMeal);
            request.setAttribute("meal", editMeal);
            request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
            return;
        }else if (request.getParameter("delete") != null && request.getParameter("delete").equals("true")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Meal deleteMeal = mealDao.getMealById(id);
            mealDao.delete(Integer.parseInt(request.getParameter("id")));
            log.debug("delete meal" + deleteMeal);

        }


        List<MealTo> mealTos = MealsUtil.filteredByStreams(mealDao.getAllMeals(), LocalTime.MIN, LocalTime.MAX, 2000);
        request.setAttribute("mealsTos", mealTos);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

}
