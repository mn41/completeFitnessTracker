package org.springframework.samples.completefitnesstracker.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "athletes")
public class Athlete extends BaseEntity{

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "athlete", fetch = FetchType.LAZY)
    private Set<Meal> meals = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "athlete", fetch = FetchType.LAZY)
    private Set<WeightMeasurement> weightMeasurements = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "athlete", fetch = FetchType.LAZY)
    private Set<Workout> workouts = new HashSet<>();

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Meal> getMeals() {
        return this.meals;
    }

    public void setMeals(Set<Meal> meals) {
        this.meals = meals;
    }

    public Set<WeightMeasurement> getWeightMeasurements() {
        return this.weightMeasurements;
    }

    public void setWeightMeasurements(Set<WeightMeasurement> weightMeasurements) {
        this.weightMeasurements = weightMeasurements;
    }

    public Set<Workout> getWorkouts() {
        return this.workouts;
    }

    public void setWorkouts(Set<Workout> workouts) {
        this.workouts = workouts;
    }

    @Override
    public String toString() {
        return "{" +
            " username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", email='" + getEmail() + "'" +
            ", meals='" + getMeals() + "'" +
            "}";
    }


}
