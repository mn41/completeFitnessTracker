package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.UserRepository;

@Profile("spring-data-jpa")
public interface SpringDataUserRepository extends UserRepository,JpaRepository<User, Integer>  {

}
