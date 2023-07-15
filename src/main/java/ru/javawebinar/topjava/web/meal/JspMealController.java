package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping("meals")
public class JspMealController extends AbstractMealController {

    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping()
    public String getAll(Model model) throws ServletException, IOException {
        model.addAttribute("mealsTo", super.getAll());
        return "meals";
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute(super.get(id));
        return "mealForm";
    }

    @GetMapping("/filter")
    public String edit(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                       @RequestParam(required = false) LocalTime startTime,
                       @RequestParam(required = false) LocalTime endTime,
                       Model model) {
        model.addAttribute("mealsTo", super.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    @GetMapping("/new")
    public String newMeal(Model model) {
        model.addAttribute("action", "new");
        model.addAttribute(new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "mealForm";
    }

    @PostMapping
    public String set(@RequestParam("id") int id,
                      @RequestParam("dateTime") String dateTime,
                      @RequestParam("description") String description,
                      @RequestParam("calories") int calories) {
        Meal meal = new Meal(id, LocalDateTime.parse(dateTime), description, calories);
        super.update(meal, id);
        return "redirect:/meals";
    }

    @PostMapping("/new")
    public String update(@RequestParam("dateTime") String dateTime,
                         @RequestParam("description") String description,
                         @RequestParam("calories") int calories) {
        Meal meal = new Meal(LocalDateTime.parse(dateTime), description, calories);
        super.create(meal);
        return "redirect:/meals";
    }

    @DeleteMapping("/{id}")
    public String deleteMeal(@PathVariable("id") int id) {
        super.delete(id);
        return "redirect:/meals";
    }
}
