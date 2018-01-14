package ru.javawebinar.topjava.to;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

public class MealTo extends BaseTo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String description;

    @Range(min = 10, max = 10000)
    @NotNull
    private Integer calories;

    @NotNull
    private LocalDateTime dateTime;

    public MealTo() {
    }

    public MealTo(Integer id, String description, Integer calories, LocalDateTime dateTime) {
        super(id);
        this.description = description;
        this.calories = calories;
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public Integer getCalories() {
        return calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", dateTime='" + dateTime + '\'' +
                '}';
    }
}
