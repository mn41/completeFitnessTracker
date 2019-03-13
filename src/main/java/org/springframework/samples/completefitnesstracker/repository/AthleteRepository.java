package org.springframework.samples.completefitnesstracker.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.completefitnesstracker.model.Athlete;

public interface AthleteRepository {

    Collection<Athlete> findAll() throws DataAccessException;

    Athlete findById(int id) throws DataAccessException;

    Athlete findByUsername(String username) throws DataAccessException;

    boolean existsByUsername (String username) throws DataAccessException;

	Athlete save(Athlete athlete) throws DataAccessException;

	void delete(Athlete athlete) throws DataAccessException;


}
