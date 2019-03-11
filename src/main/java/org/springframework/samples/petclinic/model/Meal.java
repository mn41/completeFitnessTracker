package org.springframework.samples.petclinic.model;

import java.util.HashSet;
import java.util.Set;

import java.util.ArrayList;
import java.util.List;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.samples.petclinic.rest.JacksonCustomMealDeserializer;
import org.springframework.samples.petclinic.rest.JacksonCustomMealSerializer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "meals")
@JsonSerialize(using = JacksonCustomMealSerializer.class)
@JsonDeserialize(using = JacksonCustomMealDeserializer.class)
public class Meal extends BaseEntity{

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "meal", fetch = FetchType.EAGER)
    private Set<Food> foods;

    @Column(name = "meal_name")
    private String mealName;

    @Column(name = "calories")
    private double calories;

    @Column(name = "fat")
    private double fat;

    @Column(name = "carbohydrates")
    private double carbohydrates;

    @Column(name = "protein")
    private double protein;

    @Column(name = "meal_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy/MM/dd")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "athlete_id")
    private Athlete athlete;

    public Meal() {
    }

    public Meal(int id) {
        this.setId(id);
    }

    public Meal(Set<Food> foods, String mealName, double calories, double fat, double carbohydrates, double protein) {
        this.foods = foods;
        this.mealName = mealName;
        this.calories = calories;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.protein = protein;
    }

    public String getMealName() {
        return this.mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
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

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Athlete getAthlete() {
        return this.athlete;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }

    public List<Food> getFoods() {
        List<Food> sortedFoods = new ArrayList<Food>(getFoodsInternal());
        return sortedFoods;
    }

    public void setFoods(Set<Food> foods) {
        this.foods = foods;
    }

    @JsonIgnore
    protected Set<Food> getFoodsInternal() {
        if (this.foods == null) {
            this.foods = new HashSet<>();
        }
        return this.foods;
    }

    protected void setFoodsInternal(Set<Food> foods) {
        this.foods = foods;
    }

    public void addFood(Food food) {
        getFoodsInternal().add(food);
        food.setMeal(this);
    }

    @Override
    public String toString() {
        return "{" +
            " mealName='" + getMealName() + "'" +
            ", calories='" + getCalories() + "'" +
            ", fat='" + getFat() + "'" +
            ", carbohydrates='" + getCarbohydrates() + "'" +
            ", protein='" + getProtein() + "'" +
            "}";
    }

}




