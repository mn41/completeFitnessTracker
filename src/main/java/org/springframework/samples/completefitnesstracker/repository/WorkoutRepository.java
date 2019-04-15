package org.springframework.samples.completefitnesstracker.repository;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.completefitnesstracker.model.Workout;

public interface WorkoutRepository extends JpaRepository<Workout, Integer>{

    Collection<Workout> findByAthleteId(int athleteId) throws DataAccessException;

    Collection<Workout> findTop1ByAthleteIdAndCategoryOrderByDateDesc(int athleteId, String category) throws DataAccessException;

    Collection<Workout> findByDateBetweenAndAthleteIdAndCategory(Date startDate, Date endDate, int athleteId, String category) throws DataAccessException;

	Workout findById(int id) throws DataAccessException;

}
