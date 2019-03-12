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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.samples.petclinic.model.Athlete;
import org.springframework.samples.petclinic.model.Exercise;
import org.springframework.samples.petclinic.model.Food;
import org.springframework.samples.petclinic.model.Meal;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.WeightMeasurement;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.model.Workout;
import org.springframework.samples.petclinic.repository.AthleteRepository;
import org.springframework.samples.petclinic.repository.ExerciseRepository;
import org.springframework.samples.petclinic.repository.FoodRepository;
import org.springframework.samples.petclinic.repository.MealRepository;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.PetTypeRepository;
import org.springframework.samples.petclinic.repository.SpecialtyRepository;
import org.springframework.samples.petclinic.repository.WeightMeasurementRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.samples.petclinic.repository.WorkoutRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers
 * Also a placeholder for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 * @author Vitaliy Fedoriv
 */
@Service

public class ClinicServiceImpl implements ClinicService {

    private AthleteRepository athleteRepository;
    private ExerciseRepository exerciseRepository;
    private FoodRepository foodRepository;
    private MealRepository mealRepository;
    private PetRepository petRepository;
    private VetRepository vetRepository;
    private OwnerRepository ownerRepository;
    private VisitRepository visitRepository;
    private SpecialtyRepository specialtyRepository;
    private PetTypeRepository petTypeRepository;
    private WeightMeasurementRepository weightMeasurementRepository;
    private WorkoutRepository workoutRepository;

    @Autowired
     public ClinicServiceImpl(
            AthleteRepository athleteRepository,
            ExerciseRepository exerciseRepository,
            FoodRepository foodRepository,
            MealRepository mealRepository,
            PetRepository petRepository,
            VetRepository vetRepository,
            OwnerRepository ownerRepository,
            VisitRepository visitRepository,
            SpecialtyRepository specialtyRepository,
            WeightMeasurementRepository weightMeasurementRepository,
            WorkoutRepository workoutRepository,
            PetTypeRepository petTypeRepository) {
        this.athleteRepository = athleteRepository;
        this.exerciseRepository = exerciseRepository;
        this.foodRepository = foodRepository;
        this.mealRepository = mealRepository;
        this.petRepository = petRepository;
        this.vetRepository = vetRepository;
        this.ownerRepository = ownerRepository;
        this.visitRepository = visitRepository;
        this.specialtyRepository = specialtyRepository;
        this.weightMeasurementRepository = weightMeasurementRepository;
        this.workoutRepository = workoutRepository;
		this.petTypeRepository = petTypeRepository;
    }

	@Override
	@Transactional(readOnly = true)
	public Collection<Pet> findAllPets() throws DataAccessException {
		return petRepository.findAll();
	}

	@Override
	@Transactional
	public void deletePet(Pet pet) throws DataAccessException {
		petRepository.delete(pet);
	}

	@Override
	@Transactional(readOnly = true)
	public Visit findVisitById(int visitId) throws DataAccessException {
		Visit visit = null;
		try {
			visit = visitRepository.findById(visitId);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
		// just ignore not found exceptions for Jdbc/Jpa realization
			return null;
		}
		return visit;
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Visit> findAllVisits() throws DataAccessException {
		return visitRepository.findAll();
	}

	@Override
	@Transactional
	public void deleteVisit(Visit visit) throws DataAccessException {
		visitRepository.delete(visit);
	}

	@Override
	@Transactional(readOnly = true)
	public Vet findVetById(int id) throws DataAccessException {
		Vet vet = null;
		try {
			vet = vetRepository.findById(id);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
		// just ignore not found exceptions for Jdbc/Jpa realization
			return null;
		}
		return vet;
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Vet> findAllVets() throws DataAccessException {
		return vetRepository.findAll();
	}

	@Override
	@Transactional
	public void saveVet(Vet vet) throws DataAccessException {
		vetRepository.save(vet);
	}

	@Override
	@Transactional
	public void deleteVet(Vet vet) throws DataAccessException {
		vetRepository.delete(vet);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Owner> findAllOwners() throws DataAccessException {
		return ownerRepository.findAll();
	}

	@Override
	@Transactional
	public void deleteOwner(Owner owner) throws DataAccessException {
		ownerRepository.delete(owner);
	}

	@Override
	@Transactional(readOnly = true)
	public PetType findPetTypeById(int petTypeId) {
		PetType petType = null;
		try {
			petType = petTypeRepository.findById(petTypeId);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
		// just ignore not found exceptions for Jdbc/Jpa realization
			return null;
		}
		return petType;
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<PetType> findAllPetTypes() throws DataAccessException {
		return petTypeRepository.findAll();
	}

	@Override
	@Transactional
	public void savePetType(PetType petType) throws DataAccessException {
		petTypeRepository.save(petType);
	}

	@Override
	@Transactional
	public void deletePetType(PetType petType) throws DataAccessException {
		petTypeRepository.delete(petType);
	}

	@Override
	@Transactional(readOnly = true)
	public Specialty findSpecialtyById(int specialtyId) {
		Specialty specialty = null;
		try {
			specialty = specialtyRepository.findById(specialtyId);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
		// just ignore not found exceptions for Jdbc/Jpa realization
			return null;
		}
		return specialty;
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Specialty> findAllSpecialties() throws DataAccessException {
		return specialtyRepository.findAll();
	}

	@Override
	@Transactional
	public void saveSpecialty(Specialty specialty) throws DataAccessException {
		specialtyRepository.save(specialty);
	}

	@Override
	@Transactional
	public void deleteSpecialty(Specialty specialty) throws DataAccessException {
		specialtyRepository.delete(specialty);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<PetType> findPetTypes() throws DataAccessException {
		return petRepository.findPetTypes();
	}

	@Override
	@Transactional(readOnly = true)
	public Owner findOwnerById(int id) throws DataAccessException {
		Owner owner = null;
		try {
			owner = ownerRepository.findById(id);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
		// just ignore not found exceptions for Jdbc/Jpa realization
			return null;
		}
		return owner;
	}

	@Override
	@Transactional(readOnly = true)
	public Pet findPetById(int id) throws DataAccessException {
		Pet pet = null;
		try {
			pet = petRepository.findById(id);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
		// just ignore not found exceptions for Jdbc/Jpa realization
			return null;
		}
		return pet;
	}

	@Override
	@Transactional
	public void savePet(Pet pet) throws DataAccessException {
		petRepository.save(pet);

	}

	@Override
	@Transactional
	public void saveVisit(Visit visit) throws DataAccessException {
		visitRepository.save(visit);

	}

	@Override
	@Transactional(readOnly = true)
    @Cacheable(value = "vets")
	public Collection<Vet> findVets() throws DataAccessException {
		return vetRepository.findAll();
	}

	@Override
	@Transactional
	public void saveOwner(Owner owner) throws DataAccessException {
		ownerRepository.save(owner);

	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Owner> findOwnerByLastName(String lastName) throws DataAccessException {
		return ownerRepository.findByLastName(lastName);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Visit> findVisitsByPetId(int petId) {
		return visitRepository.findByPetId(petId);
	}

    @Override
	@Transactional(readOnly = true)
	public Meal findMealById(int id) {
		Meal meal = null;
		try {
			meal = mealRepository.findById(id);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
		// just ignore not found exceptions for Jdbc/Jpa realization
			return null;
		}
		return meal;
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Meal> findAllMeals() throws DataAccessException {
		return mealRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
	public Collection<Meal> findMealsByAthleteId(int athleteId) throws DataAccessException {
		return mealRepository.findByAthleteId(athleteId);
    }

    @Override
    @Transactional(readOnly = true)
	public Collection<Meal> findMealsByDateBetweenAndAthleteId(Date startDate, Date endDate, int athleteId) throws DataAccessException {
		return mealRepository.findByDateBetweenAndAthleteId(startDate, endDate, athleteId);
	}

	@Override
	@Transactional
	public void saveMeal(Meal meal) throws DataAccessException {
		mealRepository.save(meal);
	}

	@Override
	@Transactional
	public void deleteMeal(Meal meal) throws DataAccessException {
		mealRepository.delete(meal);
    }

    @Override
	@Transactional(readOnly = true)
	public Food findFoodById(int id) {
		Food food = null;
		try {
			food = foodRepository.findById(id);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
		// just ignore not found exceptions for Jdbc/Jpa realization
			return null;
		}
		return food;
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Food> findAllFoods() throws DataAccessException {
		return foodRepository.findAll();
    }

    @Transactional(readOnly = true)
	public Collection<Food> findFoodsByMealId(int mealId) throws DataAccessException {
		return foodRepository.findByMealId(mealId);
	}

	@Override
	@Transactional
	public void saveFood(Food food) throws DataAccessException {
		foodRepository.save(food);
	}

	@Override
	@Transactional
	public void deleteFood(Food food) throws DataAccessException {
		foodRepository.delete(food);
    }

    @Override
	@Transactional(readOnly = true)
	public Athlete findAthleteById(int id) {
		Athlete athlete = null;
		try {
			athlete = athleteRepository.findById(id);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
		// just ignore not found exceptions for Jdbc/Jpa realization
			return null;
		}
		return athlete;
	}

    @Override
	@Transactional(readOnly = true)
	public Athlete findAthleteByUsername(String username) {
		Athlete athlete = null;
		try {
			athlete = athleteRepository.findByUsername(username);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
		// just ignore not found exceptions for Jdbc/Jpa realization
			return null;
		}
		return athlete;
    }

    @Override
	@Transactional(readOnly = true)
	public boolean athleteExistsByUsername(String username) {
		return athleteRepository.existsByUsername(username);
	}

    @Override
	@Transactional(readOnly = true)
	public Collection<Athlete> findAllAthletes() throws DataAccessException {
		return athleteRepository.findAll();
    }
	@Override
	@Transactional
	public void saveAthlete(Athlete athlete) throws DataAccessException {
		athleteRepository.save(athlete);
	}

	@Override
	@Transactional
	public void deleteAthlete(Athlete athlete) throws DataAccessException {
		athleteRepository.delete(athlete);
    }

    @Override
	@Transactional(readOnly = true)
	public WeightMeasurement findWeightMeasurementById(int weightMeasurementId) {
		WeightMeasurement weightMeasurement = null;
		try {
			weightMeasurement = weightMeasurementRepository.findById(weightMeasurementId);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
		// just ignore not found exceptions for Jdbc/Jpa realization
			return null;
		}
		return weightMeasurement;
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<WeightMeasurement> findAllWeightMeasurements() throws DataAccessException {
		return weightMeasurementRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
	public Collection<WeightMeasurement> findWeightMeasurementsByAthleteId(int athleteId) throws DataAccessException {
		return weightMeasurementRepository.findByAthleteId(athleteId);
    }

    @Override
    @Transactional(readOnly = true)
	public Collection<WeightMeasurement> findWeightMeasurementsByDateBetweenAndAthleteId(Date startDate, Date endDate, int athleteId) throws DataAccessException {
		return weightMeasurementRepository.findByDateBetweenAndAthleteId(startDate, endDate, athleteId);
	}

	@Override
	@Transactional
	public void saveWeightMeasurement(WeightMeasurement weightMeasurement) throws DataAccessException {
		weightMeasurementRepository.save(weightMeasurement);
	}

	@Override
	@Transactional
	public void deleteWeightMeasurement(WeightMeasurement weightMeasurement) throws DataAccessException {
		weightMeasurementRepository.delete(weightMeasurement);
    }

    @Override
	@Transactional(readOnly = true)
	public Workout findWorkoutById(int id) {
		Workout workout = null;
		try {
			workout = workoutRepository.findById(id);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
		// just ignore not found exceptions for Jdbc/Jpa realization
			return null;
		}
		return workout;
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Workout> findAllWorkouts() throws DataAccessException {
		return workoutRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
	public Collection<Workout> findWorkoutsByAthleteId(int athleteId) throws DataAccessException {
		return workoutRepository.findByAthleteId(athleteId);
    }

    @Override
    @Transactional(readOnly = true)
	public Collection<Workout> findWorkoutsByDateBetweenAndAthleteId(Date startDate, Date endDate, int athleteId) throws DataAccessException {
		return workoutRepository.findByDateBetweenAndAthleteId(startDate, endDate, athleteId);
	}

	@Override
	@Transactional
	public void saveWorkout(Workout workout) throws DataAccessException {
		workoutRepository.save(workout);
	}

	@Override
	@Transactional
	public void deleteWorkout(Workout workout) throws DataAccessException {
		workoutRepository.delete(workout);
    }

    @Override
	@Transactional(readOnly = true)
	public Exercise findExerciseById(int id) {
		Exercise exercise = null;
		try {
			exercise = exerciseRepository.findById(id);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
		// just ignore not found exceptions for Jdbc/Jpa realization
			return null;
		}
		return exercise;
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Exercise> findAllExercises() throws DataAccessException {
		return exerciseRepository.findAll();
    }

    @Transactional(readOnly = true)
	public Collection<Exercise> findExercisesByWorkoutId(int workoutId) throws DataAccessException {
		return exerciseRepository.findByWorkoutId(workoutId);
	}

	@Override
	@Transactional
	public void saveExercise(Exercise exercise) throws DataAccessException {
		exerciseRepository.save(exercise);
	}

	@Override
	@Transactional
	public void deleteExercise(Exercise exercise) throws DataAccessException {
		exerciseRepository.delete(exercise);
    }
}
