package org.springframework.samples.completefitnesstracker.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.completefitnesstracker.model.Food;

public interface FoodRepository {

    Collection<Food> findAll() throws DataAccessException;

    Collection<Food> findByMealId(int mealId) throws DataAccessException;

	Food findById(int id) throws DataAccessException;

	Food save(Food food) throws DataAccessException;

	void delete(Food food) throws DataAccessException;


}
