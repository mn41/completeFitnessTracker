package org.springframework.samples.completefitnesstracker.repository.springdatajpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Profile;
import org.springframework.samples.completefitnesstracker.model.Meal;

@Profile("spring-data-jpa")
public class SpringDataMealRepositoryImpl implements MealRepositoryOverride {

	@PersistenceContext
    private EntityManager em;

	@Override
	public void delete(Meal meal) {
		String mealId = meal.getId().toString();
		//this.em.createNativeQuery("DELETE FROM meal WHERE meal_id=" + mealId).executeUpdate();
		this.em.createQuery("DELETE FROM Meal meal WHERE id=" + mealId).executeUpdate();
	}

}
