package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;


@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends AbstractMealController {

    @GetMapping("/create")
    public String createMeal(HttpServletRequest request) {
        request.setAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "mealForm";
    }

    @GetMapping(value = "/delete/{mealId}")
    public String deleteMeal(@PathVariable int mealId) {
        delete(mealId);
        return "redirect:/meals";
    }

    @GetMapping("/update/{mealId}")
    public String update(@PathVariable int mealId, Model model) {
        model.addAttribute("meal", get(mealId));
        return "mealForm";
    }


    @PutMapping
    public String createOrUpdate(@ModelAttribute("meal") Meal meal) {
        if (meal.isNew()) {
            super.create(meal);
        } else {
            super.update(meal, meal.getId());
        }
        return "redirect:/meals";
    }

    @PostMapping("/filter")
    public String filter(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("startDate") LocalDate startDate,
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("endDate") LocalDate endDate,
                         @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) @RequestParam("startTime") LocalTime startTime,
                         @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) @RequestParam("endTime") LocalTime endTime,
                         Model model) {

        model.addAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }
}
