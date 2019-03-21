package org.springframework.samples.completefitnesstracker.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.samples.completefitnesstracker.model.Athlete;

public interface AthleteRepository extends JpaRepository<Athlete, Integer>{

    Athlete findById(int id) throws DataAccessException;

    Athlete findByUsername(String username) throws DataAccessException;

    boolean existsByUsername (String username) throws DataAccessException;

	void delete(Athlete athlete) throws DataAccessException;


}
