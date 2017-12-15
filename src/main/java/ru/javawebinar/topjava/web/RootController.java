package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;

import javax.servlet.http.HttpServletRequest;

import static ru.javawebinar.topjava.util.MealsUtil.getWithExceeded;

@Controller
public class RootController {
    @Autowired
    private UserService userService;
    @Autowired
    private MealService mealService;

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users";
    }

    @GetMapping("/meals")
    public String meals(Model model) {
        model.addAttribute("meals", getWithExceeded(mealService.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay()));
        return "meals";
    }
    @GetMapping("/meals/create")
    public String createMeal(Model model) {
        model.addAttribute("meal", new Meal());
        return "mealForm";
    }

    @DeleteMapping(value = "/meals/delete/{mealId}")
    public String deleteMeal(@PathVariable int mealId) {
        mealService.delete(mealId,  AuthorizedUser.id());
        return "meals";
    }

    @PostMapping("/users")
    public String setUser(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getParameter("userId"));
        AuthorizedUser.setId(userId);
        return "redirect:meals";
    }
}
