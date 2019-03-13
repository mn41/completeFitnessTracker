package org.springframework.samples.completefitnesstracker.repository;

import java.util.Collection;
import java.util.Date;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.completefitnesstracker.model.Workout;

public interface WorkoutRepository {

    Collection<Workout> findAll() throws DataAccessException;

    Collection<Workout> findByAthleteId(int athleteId) throws DataAccessException;

    Collection<Workout> findByDateBetweenAndAthleteId(Date startDate, Date endDate, int athleteId) throws DataAccessException;

	Workout findById(int id) throws DataAccessException;

	Workout save(Workout workout) throws DataAccessException;

	void delete(Workout workout) throws DataAccessException;


}
