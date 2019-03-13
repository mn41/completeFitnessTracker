package org.springframework.samples.completefitnesstracker.repository.springdatajpa;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.samples.completefitnesstracker.model.Meal;
import org.springframework.samples.completefitnesstracker.repository.MealRepository;

@Profile("spring-data-jpa")
public interface SpringDataMealRepository extends MealRepository,JpaRepository<Meal, Integer>, MealRepositoryOverride {

}
