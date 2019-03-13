package org.springframework.samples.completefitnesstracker.repository;

import java.util.Collection;
import java.util.Date;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.completefitnesstracker.model.WeightMeasurement;

public interface WeightMeasurementRepository {

	WeightMeasurement findById(int id) throws DataAccessException;

    Collection<WeightMeasurement> findAll() throws DataAccessException;

    Collection<WeightMeasurement> findByAthleteId(int athleteId) throws DataAccessException;

    Collection<WeightMeasurement> findByDateBetweenAndAthleteId(Date startDate, Date endDate, int athleteId) throws DataAccessException;

	WeightMeasurement save(WeightMeasurement weightMeasurement) throws DataAccessException;

	void delete(WeightMeasurement weightMeasurement) throws DataAccessException;

}
