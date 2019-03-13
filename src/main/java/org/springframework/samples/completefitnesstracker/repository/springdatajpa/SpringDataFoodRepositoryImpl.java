package org.springframework.samples.completefitnesstracker.repository.springdatajpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Profile;
import org.springframework.samples.completefitnesstracker.model.Food;

@Profile("spring-data-jpa")
public class SpringDataFoodRepositoryImpl implements FoodRepositoryOverride {

	@PersistenceContext
    private EntityManager em;

	@Override
	public void delete(Food food) {
		String foodId = food.getId().toString();
		//this.em.createNativeQuery("DELETE FROM food WHERE food_id=" + foodId).executeUpdate();
		this.em.createQuery("DELETE FROM Food food WHERE id=" + foodId).executeUpdate();
	}

}
