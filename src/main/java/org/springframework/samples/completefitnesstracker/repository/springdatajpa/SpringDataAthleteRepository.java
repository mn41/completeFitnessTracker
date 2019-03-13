package org.springframework.samples.completefitnesstracker.repository.springdatajpa;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.samples.completefitnesstracker.model.Athlete;
import org.springframework.samples.completefitnesstracker.repository.AthleteRepository;

@Profile("spring-data-jpa")
public interface SpringDataAthleteRepository extends AthleteRepository,JpaRepository<Athlete, Integer>, AthleteRepositoryOverride {


}
