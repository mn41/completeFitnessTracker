package org.springframework.samples.completefitnesstracker.service;

import java.util.Collection;
import java.util.Date;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.completefitnesstracker.model.Athlete;
import org.springframework.samples.completefitnesstracker.model.Food;
import org.springframework.samples.completefitnesstracker.model.Meal;
import org.springframework.samples.completefitnesstracker.model.Exercise;
import org.springframework.samples.completefitnesstracker.model.WeightMeasurement;
import org.springframework.samples.completefitnesstracker.model.Workout;

public interface TrackerService {

    Athlete findAthleteById(int id);
    Athlete findAthleteByUsername(String username);
    boolean athleteExistsByUsername(String username);
	Collection<Athlete> findAllAthletes() throws DataAccessException;
	void saveAthlete(Athlete athlete) throws DataAccessException;
    void deleteAthlete(Athlete athlete) throws DataAccessException;

    Meal findMealById(int id);
    Collection<Meal> findAllMeals() throws DataAccessException;
    Collection<Meal> findMealsByAthleteId(int athleteId);
    Collection<Meal> findMealsByDateBetweenAndAthleteId(Date startDate, Date endDate, int athleteId) throws DataAccessException;
    Collection<Meal> findRecentMealByAthleteId(int athleteId);
    void saveMeal(Meal meal) throws DataAccessException;
    void deleteMeal(Meal meal) throws DataAccessException;

    Food findFoodById(int id);
    Collection<Food> findAllFoods() throws DataAccessException;
    Collection<Food> findFoodsByMealId(int mealId) throws DataAccessException;
	void saveFood(Food food) throws DataAccessException;
	void deleteFood(Food food) throws DataAccessException;

    WeightMeasurement findWeightMeasurementById(int weightMeasurementId);
    Collection<WeightMeasurement> findAllWeightMeasurements() throws DataAccessException;
    Collection<WeightMeasurement> findWeightMeasurementsByAthleteId(int athleteId) throws DataAccessException;
    Collection<WeightMeasurement> findWeightMeasurementsByDateBetweenAndAthleteId(Date startDate, Date endDate, int athleteId) throws DataAccessException;
	void saveWeightMeasurement(WeightMeasurement weightMeasurement) throws DataAccessException;
    void deleteWeightMeasurement(WeightMeasurement weightMeasurement) throws DataAccessException;

    Workout findWorkoutById(int id);
    Collection<Workout> findAllWorkouts() throws DataAccessException;
    Collection<Workout> findRecentWorkoutByAthleteId(int athleteId);
    Collection<Workout> findWorkoutsByAthleteId(int athleteId);
    Collection<Workout> findWorkoutsByDateBetweenAndAthleteId(Date startDate, Date endDate, int athleteId) throws DataAccessException;
	void saveWorkout(Workout workout) throws DataAccessException;
    void deleteWorkout(Workout workout) throws DataAccessException;

    Exercise findExerciseById(int id);
    Collection<Exercise> findAllExercises() throws DataAccessException;
    Collection<Exercise> findExercisesByWorkoutId(int workoutId) throws DataAccessException;
    Collection<Exercise> findExercisesByDateBetweenAndWorkoutId(Date startDate, Date endDate, int workoutId) throws DataAccessException;
    Collection<Exercise> findExercisesByDateBetweenAndExerciseNameAndWorkoutId(Date startDate, Date endDate, String exerciseName, int workoutId) throws DataAccessException;
	void saveExercise(Exercise exercise) throws DataAccessException;
	void deleteExercise(Exercise exercise) throws DataAccessException;
}
