package org.springframework.samples.completefitnesstracker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.samples.completefitnesstracker.rest.JacksonCustomFoodDeserializer;
import org.springframework.samples.completefitnesstracker.rest.JacksonCustomFoodSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "foods")
@JsonSerialize(using = JacksonCustomFoodSerializer.class)
@JsonDeserialize(using = JacksonCustomFoodDeserializer.class)
public class Food extends BaseEntity{

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "servings")
    private double servings;

    @Column(name = "calories")
    private double calories;

    @Column(name = "fat")
    private double fat;

    @Column(name = "carbohydrates")
    private double carbohydrates;

    @Column(name = "protein")
    private double protein;

    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;

    public Food() {
    }

    public Food(String foodName, double calories, double fat, double carbohydrates, double protein, double servings) {
        this.foodName = foodName;
        this.calories = calories;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.protein = protein;
        this.servings = servings;
    }

    public String getFoodName() {
        return this.foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getServings() {
        return this.servings;
    }

    public void setServings(double servings) {
        this.servings = servings;
    }

    public double getCalories() {
        return this.calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getFat() {
        return this.fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarbohydrates() {
        return this.carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public double getProtein() {
        return this.protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public Meal getMeal() {
        return this.meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    @Override
    public String toString() {
        return "{" +
            " foodName='" + getFoodName() + "'" +
            ", calories='" + getCalories() + "'" +
            ", fat='" + getFat() + "'" +
            ", carbohydrates='" + getCarbohydrates() + "'" +
            ", protein='" + getProtein() + "'" +
            "}";
    }

}
