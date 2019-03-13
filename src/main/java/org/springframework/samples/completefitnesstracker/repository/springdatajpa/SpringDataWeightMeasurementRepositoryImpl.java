package org.springframework.samples.completefitnesstracker.repository.springdatajpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Profile;
import org.springframework.samples.completefitnesstracker.model.WeightMeasurement;

@Profile("spring-data-jpa")
public class SpringDataWeightMeasurementRepositoryImpl implements WeightMeasurementRepositoryOverride {

	@PersistenceContext
    private EntityManager em;

	@Override
	public void delete(WeightMeasurement weightMeasurement) {
		String wmId = weightMeasurement.getId().toString();
		this.em.createQuery("DELETE FROM WeightMeasurement weightMeasurement WHERE id=" + wmId).executeUpdate();
	}

}
