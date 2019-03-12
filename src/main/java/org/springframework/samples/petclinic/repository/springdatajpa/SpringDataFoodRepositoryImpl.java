/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Profile;
import org.springframework.samples.petclinic.model.Food;

/**
 * @author Vitaliy Fedoriv
 *
 */

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
