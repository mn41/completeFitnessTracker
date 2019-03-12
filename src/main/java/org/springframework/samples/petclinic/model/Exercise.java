package org.springframework.samples.petclinic.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.samples.petclinic.rest.JacksonCustomExerciseDeserializer;
import org.springframework.samples.petclinic.rest.JacksonCustomExerciseSerializer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "exercises")
@JsonSerialize(using = JacksonCustomExerciseSerializer.class)
@JsonDeserialize(using = JacksonCustomExerciseDeserializer.class)
public class Exercise extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;

    @Column(name = "exercise_name")
    private String exerciseName;

    @Column(name = "weight")
    private double weight;

    @Column(name = "reps")
    private double reps;

    @Column (name = "elapsed_time")
    private double elapsedTime;

    //Order that the exercise was done in workout
    @Column (name = "sequence_number")
    private int sequenceNumber;


    public Exercise() {
    }

    public Exercise(String exerciseName, double weight, double reps, double elapsedTime, Workout workout) {
        this.exerciseName = exerciseName;
        this.weight = weight;
        this.reps = reps;
        this.elapsedTime = elapsedTime;
        this.workout = workout;
    }

    public String getExerciseName() {
        return this.exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getReps() {
        return this.reps;
    }

    public void setReps(double reps) {
        this.reps = reps;
    }

    public double getElapsedTime() {
        return this.elapsedTime;
    }

    public void setElapsedTime(double elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public int getSequenceNumber() {
        return this.sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public Workout getWorkout() {
        return this.workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public Exercise exerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
        return this;
    }

    public Exercise weight(double weight) {
        this.weight = weight;
        return this;
    }

    public Exercise reps(double reps) {
        this.reps = reps;
        return this;
    }

    public Exercise elapsedTime(double elapsedTime) {
        this.elapsedTime = elapsedTime;
        return this;
    }

    public Exercise workout(Workout workout) {
        this.workout = workout;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " exerciseName='" + getExerciseName() + "'" +
            ", weight='" + getWeight() + "'" +
            ", reps='" + getReps() + "'" +
            ", elapsedTime='" + getElapsedTime() + "'" +
            ", workout='" + getWorkout() + "'" +
            "}";
    }

}
