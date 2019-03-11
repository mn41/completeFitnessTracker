package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.UserRepository;

@Profile("spring-data-jpa")
public interface SpringDataUserRepository extends UserRepository, PagingAndSortingRepository<User, Integer>  {

}
