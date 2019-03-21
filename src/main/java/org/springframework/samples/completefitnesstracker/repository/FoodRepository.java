package org.springframework.samples.completefitnesstracker.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.completefitnesstracker.model.Food;

public interface FoodRepository extends JpaRepository<Food, Integer>{

    Collection<Food> findByMealId(int mealId) throws DataAccessException;

	Food findById(int id) throws DataAccessException;

}
