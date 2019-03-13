/*
 * Copyright 2016-2018 the original author or authors.
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
package org.springframework.samples.petclinic.rest;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Food;
import org.springframework.samples.petclinic.model.Meal;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Vitaliy Fedoriv
 *
 */

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/food")
public class FoodRestController {

    @Autowired
	private ClinicService clinicService;

	@PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Food>> getAllFoods(){
		Collection<Food> food = new ArrayList<Food>();
		food.addAll(this.clinicService.findAllFoods());
		if (food.isEmpty()){
			return new ResponseEntity<Collection<Food>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Food>>(food, HttpStatus.OK);
    }

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/search/meal/{mealId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Food>> getFoodsByMealId(@PathVariable("mealId") int mealId){
		Collection<Food> food = new ArrayList<Food>();
		food.addAll(this.clinicService.findFoodsByMealId(mealId));
		if (food.isEmpty()){
			return new ResponseEntity<Collection<Food>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Food>>(food, HttpStatus.OK);
	}

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/{foodId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Food> getFood(@PathVariable("foodId") int foodId){
		Food food = this.clinicService.findFoodById(foodId);
		if(food == null){
			return new ResponseEntity<Food>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Food>(food, HttpStatus.OK);
	}

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/add/{mealId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Food> addFood(@PathVariable("mealId") int mealId, @RequestBody @Valid Food food, BindingResult bindingResult, UriComponentsBuilder ucBuilder){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (food == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Food>(headers, HttpStatus.BAD_REQUEST);
        }
        Meal meal = this.clinicService.findMealById(mealId);
        food.setMeal(meal);
        this.clinicService.saveFood(food);
        meal.updateTotals();
		headers.setLocation(ucBuilder.path("/api/foods/{id}").buildAndExpand(food.getId()).toUri());
		return new ResponseEntity<Food>(food, headers, HttpStatus.CREATED);
	}

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/{foodId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Food> updateFood(@PathVariable("foodId") int foodId, @RequestBody @Valid Food food, BindingResult bindingResult){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (food == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Food>(headers, HttpStatus.BAD_REQUEST);
		}
		Food currentFood = this.clinicService.findFoodById(foodId);
		if(currentFood == null){
			return new ResponseEntity<Food>(HttpStatus.NOT_FOUND);
        }

        currentFood.setFoodName(food.getFoodName());
        currentFood.setCalories(food.getCalories());
        currentFood.setFat(food.getFat());
        currentFood.setCarbohydrates(food.getCarbohydrates());
        currentFood.setProtein(food.getProtein());
        currentFood.setServings(food.getServings());

		this.clinicService.saveFood(currentFood);
		return new ResponseEntity<Food>(currentFood, HttpStatus.NO_CONTENT);
	}

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/{foodId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	public ResponseEntity<Void> deleteFood(@PathVariable("foodId") int foodId){
		Food food = this.clinicService.findFoodById(foodId);
		if(food == null){
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.clinicService.deleteFood(food);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
