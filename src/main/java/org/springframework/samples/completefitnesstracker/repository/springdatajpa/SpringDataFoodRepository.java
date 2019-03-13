package org.springframework.samples.completefitnesstracker.repository.springdatajpa;

import java.util.Collection;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.samples.completefitnesstracker.model.Food;
import org.springframework.samples.completefitnesstracker.repository.FoodRepository;

@Profile("spring-data-jpa")
public interface SpringDataFoodRepository extends FoodRepository,JpaRepository<Food, Integer>, FoodRepositoryOverride {

    Collection<Food> findByMealId(int meal_id);

}
