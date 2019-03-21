package org.springframework.samples.completefitnesstracker.repository;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.completefitnesstracker.model.WeightMeasurement;

public interface WeightMeasurementRepository extends JpaRepository<WeightMeasurement, Integer>{

	WeightMeasurement findById(int id) throws DataAccessException;

    Collection<WeightMeasurement> findByAthleteId(int athleteId) throws DataAccessException;

    Collection<WeightMeasurement> findByDateBetweenAndAthleteId(Date startDate, Date endDate, int athleteId) throws DataAccessException;

}
