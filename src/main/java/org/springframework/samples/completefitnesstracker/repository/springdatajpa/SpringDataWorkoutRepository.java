package org.springframework.samples.completefitnesstracker.repository.springdatajpa;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.samples.completefitnesstracker.model.Workout;
import org.springframework.samples.completefitnesstracker.repository.WorkoutRepository;

@Profile("spring-data-jpa")
public interface SpringDataWorkoutRepository extends WorkoutRepository,JpaRepository<Workout, Integer>, WorkoutRepositoryOverride {

    public void delete(Workout workout);
}
