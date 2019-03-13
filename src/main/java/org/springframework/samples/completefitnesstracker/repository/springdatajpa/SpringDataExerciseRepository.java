package org.springframework.samples.completefitnesstracker.repository.springdatajpa;

import java.util.Collection;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.samples.completefitnesstracker.model.Exercise;
import org.springframework.samples.completefitnesstracker.repository.ExerciseRepository;

@Profile("spring-data-jpa")
public interface SpringDataExerciseRepository extends ExerciseRepository,JpaRepository<Exercise, Integer>, ExerciseRepositoryOverride {

    Collection<Exercise> findByWorkoutId(int workout_id);

}
