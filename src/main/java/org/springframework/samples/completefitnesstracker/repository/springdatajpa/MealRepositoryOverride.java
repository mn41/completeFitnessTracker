package org.springframework.samples.completefitnesstracker.repository.springdatajpa;

import org.springframework.context.annotation.Profile;
import org.springframework.samples.completefitnesstracker.model.Meal;

@Profile("spring-data-jpa")
public interface MealRepositoryOverride {

	public void delete(Meal meal);

}
