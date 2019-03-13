package org.springframework.samples.completefitnesstracker.repository.springdatajpa;

import org.springframework.context.annotation.Profile;
import org.springframework.samples.completefitnesstracker.model.Workout;

@Profile("spring-data-jpa")
public interface WorkoutRepositoryOverride {

	public void delete(Workout workout);

}
