package org.springframework.samples.completefitnesstracker.repository.springdatajpa;

import org.springframework.context.annotation.Profile;
import org.springframework.samples.completefitnesstracker.model.Food;

@Profile("spring-data-jpa")
public interface FoodRepositoryOverride {

	public void delete(Food food);

}
