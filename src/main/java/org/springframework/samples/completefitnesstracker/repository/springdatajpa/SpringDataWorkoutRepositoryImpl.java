package org.springframework.samples.completefitnesstracker.repository.springdatajpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Profile;
import org.springframework.samples.completefitnesstracker.model.Workout;

@Profile("spring-data-jpa")
public class SpringDataWorkoutRepositoryImpl implements WorkoutRepositoryOverride {

	@PersistenceContext
    private EntityManager em;

	@Override
	public void delete(Workout workout) {
		String workoutId = workout.getId().toString();
		//this.em.createNativeQuery("DELETE FROM workout WHERE workout_id=" + workoutId).executeUpdate();
		this.em.createQuery("DELETE FROM Workout workout WHERE id=" + workoutId).executeUpdate();
	}

}
