package ru.javawebinar.topjava.controller;

import ru.javawebinar.topjava.dao.InMemoryMealDaoImpl;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MealController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/meal.jsp";
    private static String MEAL_LIST = "/mealList.jsp";
    private MealDao dao;

    public MealController() {
        super();
        dao = new InMemoryMealDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            forward = MEAL_LIST;
            request.setAttribute("mealList", dao.getAll());
        } else if (action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            dao.delete(mealId);
            forward = MEAL_LIST;
            request.setAttribute("mealList", dao.getAll());
        } else if (action.equalsIgnoreCase("edit")) {
            forward = INSERT_OR_EDIT;
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = dao.getById(mealId);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("mealList")) {
            forward = MEAL_LIST;
            request.setAttribute("mealList", dao.getAll());
        } else {
            forward = INSERT_OR_EDIT;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mealId = request.getParameter("mealId");
        int id = mealId == null ? 0 : mealId.isEmpty() ? 0 : Integer.parseInt(mealId);
        Meal meal = id == 0 ? new Meal() : new Meal(id);
        meal.setDescription(request.getParameter("description"));
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));
        LocalDateTime mealDateTime = LocalDateTime.parse(request.getParameter("mealDateTime")
                , DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        meal.setDateTime(mealDateTime);

        if (id == 0) {
            dao.add(meal);
        } else {
            dao.update(meal);
        }
        RequestDispatcher view = request.getRequestDispatcher(MEAL_LIST);
        request.setAttribute("meals", dao.getAll());
        view.forward(request, response);
    }

}
