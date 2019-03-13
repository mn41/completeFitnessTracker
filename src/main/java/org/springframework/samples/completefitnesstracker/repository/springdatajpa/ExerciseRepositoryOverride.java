package org.springframework.samples.completefitnesstracker.repository.springdatajpa;

import org.springframework.context.annotation.Profile;
import org.springframework.samples.completefitnesstracker.model.Exercise;

@Profile("spring-data-jpa")
public interface ExerciseRepositoryOverride {

	public void delete(Exercise exercise);

}
