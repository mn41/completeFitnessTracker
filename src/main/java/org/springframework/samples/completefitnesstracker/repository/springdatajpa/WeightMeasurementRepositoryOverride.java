package org.springframework.samples.completefitnesstracker.repository.springdatajpa;

import org.springframework.context.annotation.Profile;
import org.springframework.samples.completefitnesstracker.model.WeightMeasurement;

@Profile("spring-data-jpa")
public interface WeightMeasurementRepositoryOverride {

	public void delete(WeightMeasurement weightMeasurement);

}
