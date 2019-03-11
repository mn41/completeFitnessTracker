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

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Athlete;
import org.springframework.samples.petclinic.model.Food;
import org.springframework.samples.petclinic.model.Meal;
import org.springframework.samples.petclinic.model.Exercise;
import org.springframework.samples.petclinic.model.Workout;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.WeightMeasurement;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;


/**
 * Mostly used as a facade so all controllers have a single point of entry
 *
 * @author Michael Isvy
 * @author Vitaliy Fedoriv
 */
public interface ClinicService {

	Pet findPetById(int id) throws DataAccessException;
	Collection<Pet> findAllPets() throws DataAccessException;
	void savePet(Pet pet) throws DataAccessException;
	void deletePet(Pet pet) throws DataAccessException;

	Collection<Visit> findVisitsByPetId(int petId);
	Visit findVisitById(int visitId) throws DataAccessException;
	Collection<Visit> findAllVisits() throws DataAccessException;
	void saveVisit(Visit visit) throws DataAccessException;
	void deleteVisit(Visit visit) throws DataAccessException;

	Vet findVetById(int id) throws DataAccessException;
	Collection<Vet> findVets() throws DataAccessException;
	Collection<Vet> findAllVets() throws DataAccessException;
	void saveVet(Vet vet) throws DataAccessException;
	void deleteVet(Vet vet) throws DataAccessException;

	Owner findOwnerById(int id) throws DataAccessException;
	Collection<Owner> findAllOwners() throws DataAccessException;
	void saveOwner(Owner owner) throws DataAccessException;
	void deleteOwner(Owner owner) throws DataAccessException;
	Collection<Owner> findOwnerByLastName(String lastName) throws DataAccessException;

	PetType findPetTypeById(int petTypeId);
	Collection<PetType> findAllPetTypes() throws DataAccessException;
	Collection<PetType> findPetTypes() throws DataAccessException;
	void savePetType(PetType petType) throws DataAccessException;
	void deletePetType(PetType petType) throws DataAccessException;

	Specialty findSpecialtyById(int specialtyId);
	Collection<Specialty> findAllSpecialties() throws DataAccessException;
	void saveSpecialty(Specialty specialty) throws DataAccessException;
    void deleteSpecialty(Specialty specialty) throws DataAccessException;

    Athlete findAthleteByUsername(String username);
	Collection<Athlete> findAllAthletes() throws DataAccessException;
	void saveAthlete(Athlete athlete) throws DataAccessException;
    void deleteAthlete(Athlete athlete) throws DataAccessException;

    Meal findMealById(int id);
    Collection<Meal> findAllMeals() throws DataAccessException;
    Collection<Meal> findMealsByAthleteId(int athleteId);
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
	void saveWeightMeasurement(WeightMeasurement weightMeasurement) throws DataAccessException;
    void deleteWeightMeasurement(WeightMeasurement weightMeasurement) throws DataAccessException;

    Workout findWorkoutById(int id);
    Collection<Workout> findAllWorkouts() throws DataAccessException;
    Collection<Workout> findWorkoutsByAthleteId(int athleteId);
	void saveWorkout(Workout workout) throws DataAccessException;
    void deleteWorkout(Workout workout) throws DataAccessException;

    Exercise findExerciseById(int id);
    Collection<Exercise> findAllExercises() throws DataAccessException;
    Collection<Exercise> findExercisesByWorkoutId(int workoutId) throws DataAccessException;
	void saveExercise(Exercise exercise) throws DataAccessException;
	void deleteExercise(Exercise exercise) throws DataAccessException;
}
