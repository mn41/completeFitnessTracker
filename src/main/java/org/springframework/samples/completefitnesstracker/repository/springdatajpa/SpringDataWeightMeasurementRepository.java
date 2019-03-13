package org.springframework.samples.completefitnesstracker.repository.springdatajpa;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.samples.completefitnesstracker.model.WeightMeasurement;
import org.springframework.samples.completefitnesstracker.repository.WeightMeasurementRepository;

@Profile("spring-data-jpa")
public interface SpringDataWeightMeasurementRepository extends WeightMeasurementRepository,JpaRepository<WeightMeasurement, Integer>, WeightMeasurementRepositoryOverride {

}
