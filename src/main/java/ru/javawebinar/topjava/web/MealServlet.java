package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private static final int MAX_COLORIES_PER_DAY = 2000;
    private final MealDao mealDao = new MealDaoImpl();

    @Override
    public void init() throws ServletException {
        super.init();
        //initial filling
        mealDao.addMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
        mealDao.addMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
        mealDao.addMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
        mealDao.addMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
        mealDao.addMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
        mealDao.addMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
        mealDao.addMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
        log.debug("Init DAO");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        if (request.getParameter("add") != null && request.getParameter("add").equals("true")) {
            request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
            return;
        } else if (request.getParameter("saveAdd") != null && request.getParameter("saveAdd").equals("true")) {
            String datetime = request.getParameter("datetime");
            String description = request.getParameter("description");
            int calories = Integer.parseInt(request.getParameter("calories"));
            LocalDateTime localDateTime = LocalDateTime.parse(datetime);

            if (request.getParameter("id").equals("")) {
                mealDao.addMeal(localDateTime, description, calories);
                log.debug("Added new meal");
            } else {
                mealDao.updateMeal(new Meal(Integer.parseInt(request.getParameter("id")), localDateTime, description, calories));
                log.debug("Edit meal {}", request.getParameter("id"));
            }
        } else if (request.getParameter("edit") != null && request.getParameter("edit").equals("edit")) {
            Meal editMeal = mealDao.getMealById(Integer.parseInt(request.getParameter("id")));
            log.debug("Start edit meal {}", editMeal);
            request.setAttribute("meal", editMeal);
            request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
            return;
        } else if (request.getParameter("delete") != null && request.getParameter("delete").equals("true")) {
            int id = Integer.parseInt(request.getParameter("id"));
            mealDao.delete(id);
            log.debug("Delete {}", id);
            response.sendRedirect(request.getContextPath() + "/meals");
            return;
        }

        List<MealTo> mealTos = MealsUtil.filteredByStreams(mealDao.getAllMeals(), LocalTime.MIN, LocalTime.MAX, MAX_COLORIES_PER_DAY);
        request.setAttribute("mealsTos", mealTos);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}

