package org.springframework.samples.completefitnesstracker.service;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.samples.completefitnesstracker.model.Athlete;
import org.springframework.samples.completefitnesstracker.model.Exercise;
import org.springframework.samples.completefitnesstracker.model.Food;
import org.springframework.samples.completefitnesstracker.model.Meal;
import org.springframework.samples.completefitnesstracker.model.WeightMeasurement;
import org.springframework.samples.completefitnesstracker.model.Workout;
import org.springframework.samples.completefitnesstracker.repository.AthleteRepository;
import org.springframework.samples.completefitnesstracker.repository.ExerciseRepository;
import org.springframework.samples.completefitnesstracker.repository.FoodRepository;
import org.springframework.samples.completefitnesstracker.repository.MealRepository;
import org.springframework.samples.completefitnesstracker.repository.WeightMeasurementRepository;
import org.springframework.samples.completefitnesstracker.repository.WorkoutRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TrackerServiceImpl implements TrackerService {

    private AthleteRepository athleteRepository;
    private ExerciseRepository exerciseRepository;
    private FoodRepository foodRepository;
    private MealRepository mealRepository;
    private WeightMeasurementRepository weightMeasurementRepository;
    private WorkoutRepository workoutRepository;

    @Autowired
     public TrackerServiceImpl(
            AthleteRepository athleteRepository,
            ExerciseRepository exerciseRepository,
            FoodRepository foodRepository,
            MealRepository mealRepository,
            WeightMeasurementRepository weightMeasurementRepository,
            WorkoutRepository workoutRepository) {
        this.athleteRepository = athleteRepository;
        this.exerciseRepository = exerciseRepository;
        this.foodRepository = foodRepository;
        this.mealRepository = mealRepository;
        this.weightMeasurementRepository = weightMeasurementRepository;
        this.workoutRepository = workoutRepository;
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
    @Transactional(readOnly = true)
	public Collection<Meal> findRecentMealByAthleteId(int athleteId) throws DataAccessException {
		return mealRepository.findTop1ByAthleteIdOrderByDateDesc(athleteId);
    }

    @Override
    @Transactional(readOnly = true)
	public Collection<Meal> findRecentMealDateByAthleteId(int athleteId) throws DataAccessException {
		return mealRepository.findTop1ByAthleteIdOrderByDateDesc(athleteId);
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
	public Collection<Workout> findRecentWorkoutByAthleteIdAndCategory(int athleteId, String category) throws DataAccessException {
		return workoutRepository.findTop1ByAthleteIdAndCategoryOrderByDateDesc(athleteId, category);
    }

    @Override
    @Transactional(readOnly = true)
	public Collection<Workout> findWorkoutsByAthleteId(int athleteId) throws DataAccessException {
		return workoutRepository.findByAthleteId(athleteId);
    }

    @Override
    @Transactional(readOnly = true)
	public Collection<Workout> findWorkoutsByDateBetweenAndAthleteIdAndCategory(Date startDate, Date endDate, int athleteId, String category) throws DataAccessException {
		return workoutRepository.findByDateBetweenAndAthleteIdAndCategory(startDate, endDate, athleteId, category);
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
    @Transactional(readOnly = true)
	public Collection<Exercise> findExercisesByDateBetweenAndWorkoutId(Date startDate, Date endDate, int workoutId) throws DataAccessException {
		return exerciseRepository.findByDateBetweenAndWorkoutId(startDate, endDate, workoutId);
    }

    @Override
    @Transactional(readOnly = true)
	public Collection<Exercise> findExercisesByDateBetweenAndExerciseNameAndWorkoutId(Date startDate, Date endDate, String exerciseName, int workoutId) throws DataAccessException {
		return exerciseRepository.findByDateBetweenAndExerciseNameAndWorkoutId(startDate, endDate, exerciseName, workoutId);
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
