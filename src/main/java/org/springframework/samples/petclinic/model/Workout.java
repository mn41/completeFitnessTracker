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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.samples.petclinic.rest.JacksonCustomWorkoutDeserializer;
import org.springframework.samples.petclinic.rest.JacksonCustomWorkoutSerializer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "workouts")
@JsonSerialize(using = JacksonCustomWorkoutSerializer.class)
@JsonDeserialize(using = JacksonCustomWorkoutDeserializer.class)
public class Workout extends BaseEntity{

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workout", fetch = FetchType.EAGER)
    private Set<Exercise> exercises;

    @Column(name = "workout_name")
    private String workoutName;

    @Column(name = "category")
    private String category;

    @Column(name = "workout_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy/MM/dd")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "athlete_id")
    private Athlete athlete;

    public Workout() {
    }

    public Workout(int id) {
        this.setId(id);
    }

    public Workout(Set<Exercise> exercises, String workoutName, String category, Date date) {
        this.exercises = exercises;
        this.workoutName = workoutName;
        this.category = category;
        this.date = date;
    }


    public Workout(Set<Exercise> exercises, String workoutName, String category, Date date, Athlete athlete) {
        this.exercises = exercises;
        this.workoutName = workoutName;
        this.category = category;
        this.date = date;
        this.athlete = athlete;
    }

    public String getWorkoutName() {
        return this.workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public String getWorkoutCategory() {
        return this.category;
    }

    public void setWorkoutCategory(String category) {
        this.category = category;
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

    public Workout exercises(Set<Exercise> exercises) {
        this.exercises = exercises;
        return this;
    }

    public Workout workoutName(String workoutName) {
        this.workoutName = workoutName;
        return this;
    }

    public Workout category(String category) {
        this.category = category;
        return this;
    }

    public Workout date(Date date) {
        this.date = date;
        return this;
    }

    public Workout athlete(Athlete athlete) {
        this.athlete = athlete;
        return this;
    }

    public List<Exercise> getExercises() {
        List<Exercise> sortedExercises = new ArrayList<Exercise>(getExercisesInternal());
        return sortedExercises;
    }

    public void setExercises(Set<Exercise> exercises) {
        this.exercises = exercises;
    }

    @JsonIgnore
    protected Set<Exercise> getExercisesInternal() {
        if (this.exercises == null) {
            this.exercises = new HashSet<>();
        }
        return this.exercises;
    }

    protected void setExercisesInternal(Set<Exercise> exercises) {
        this.exercises = exercises;
    }

    public void addExercise(Exercise exercise) {
        getExercisesInternal().add(exercise);
        exercise.setWorkout(this);
    }

    @Override
    public String toString() {
        return "{" +
            " exercises='" + getExercises() + "'" +
            ", workoutName='" + getWorkoutName() + "'" +
            ", category='" + getWorkoutCategory() + "'" +
            ", date='" + getDate() + "'" +
            ", athlete='" + getAthlete() + "'" +
            "}";
    }


}



