package org.springframework.samples.completefitnesstracker.repository.springdatajpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Profile;
import org.springframework.samples.completefitnesstracker.model.Exercise;

@Profile("spring-data-jpa")
public class SpringDataExerciseRepositoryImpl implements ExerciseRepositoryOverride {

	@PersistenceContext
    private EntityManager em;

	@Override
	public void delete(Exercise exercise) {
		String exerciseId = exercise.getId().toString();
		//this.em.createNativeQuery("DELETE FROM exercise WHERE exercise_id=" + exerciseId).executeUpdate();
		this.em.createQuery("DELETE FROM Exercise exercise WHERE id=" + exerciseId).executeUpdate();
	}

}
