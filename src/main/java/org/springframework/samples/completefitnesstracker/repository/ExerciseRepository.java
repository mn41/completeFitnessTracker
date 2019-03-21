package org.springframework.samples.completefitnesstracker.repository;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.completefitnesstracker.model.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer>{

    Collection<Exercise> findByWorkoutId(int workoutId) throws DataAccessException;

    Collection<Exercise> findByDateBetweenAndWorkoutId(Date startDate, Date endDate, int workoutId) throws DataAccessException;

    Collection<Exercise> findByDateBetweenAndExerciseNameAndWorkoutId(Date startDate, Date endDate, String category, int workoutId) throws DataAccessException;

	Exercise findById(int id) throws DataAccessException;

}
