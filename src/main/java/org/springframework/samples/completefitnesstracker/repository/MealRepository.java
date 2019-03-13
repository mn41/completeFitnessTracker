package org.springframework.samples.completefitnesstracker.repository;

import java.util.Collection;
import java.util.Date;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.completefitnesstracker.model.Meal;

public interface MealRepository {

    Collection<Meal> findAll() throws DataAccessException;

    Collection<Meal> findByAthleteId(int athleteId) throws DataAccessException;

    Collection<Meal> findByDateBetweenAndAthleteId(Date startDate, Date endDate, int athleteId) throws DataAccessException;

	Meal findById(int id) throws DataAccessException;

	Meal save(Meal meal) throws DataAccessException;

	void delete(Meal meal) throws DataAccessException;


}
