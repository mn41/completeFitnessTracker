package org.springframework.samples.completefitnesstracker.repository;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.completefitnesstracker.model.Meal;

public interface MealRepository  extends JpaRepository<Meal, Integer>{

    Collection<Meal> findByAthleteId(int athleteId) throws DataAccessException;

    Collection<Meal> findTop1ByAthleteIdOrderByDateDesc(int athleteId) throws DataAccessException;

    Collection<Meal> findByDateBetweenAndAthleteId(Date startDate, Date endDate, int athleteId) throws DataAccessException;

	Meal findById(int id) throws DataAccessException;

}
