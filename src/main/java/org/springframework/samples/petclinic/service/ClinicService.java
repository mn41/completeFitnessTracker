/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Date;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Athlete;
import org.springframework.samples.petclinic.model.Food;
import org.springframework.samples.petclinic.model.Meal;
import org.springframework.samples.petclinic.model.Exercise;
import org.springframework.samples.petclinic.model.WeightMeasurement;
import org.springframework.samples.petclinic.model.Workout;

public interface ClinicService {

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
