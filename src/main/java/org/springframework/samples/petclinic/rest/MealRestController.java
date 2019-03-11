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
import org.springframework.samples.petclinic.model.Athlete;
import org.springframework.samples.petclinic.model.Food;
import org.springframework.samples.petclinic.model.Meal;
import org.springframework.samples.petclinic.model.Vet;
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
@RequestMapping("api/meal")
public class MealRestController {

    @Autowired
	private ClinicService clinicService;

	@PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Meal>> getAllMeals(){
		Collection<Meal> meal = new ArrayList<Meal>();
		meal.addAll(this.clinicService.findAllMeals());
		if (meal.isEmpty()){
			return new ResponseEntity<Collection<Meal>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Meal>>(meal, HttpStatus.OK);
    }


    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/totals/{mealId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Meal> getMealTotals(@PathVariable("mealId") int mealId){
        Meal meal = this.clinicService.findMealById(mealId);
        int fat = 0;
        int carbohydrates = 0;
        int protein = 0;
        int calories = 0;
		for (Food f : meal.getFoods()){
            fat += f.getFat();
            carbohydrates += f.getCarbohydrates();
            protein += f.getProtein();
        }
        calories = fat*9 + carbohydrates*4 + protein*4;
        meal.setCalories(calories);
        meal.setFat(fat);
        meal.setCarbohydrates(carbohydrates);
        meal.setProtein(protein);
		return new ResponseEntity<Meal>(meal, HttpStatus.OK);
    }

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/{mealId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Meal> getMeal(@PathVariable("mealId") int mealId){
		Meal meal = this.clinicService.findMealById(mealId);
		if(meal == null){
			return new ResponseEntity<Meal>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Meal>(meal, HttpStatus.OK);
    }

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/athlete/{athleteId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Meal>> getMealByAthleteId(@PathVariable("athleteId") int athleteId){
		Collection<Meal> meal = new ArrayList<Meal>();
		meal.addAll(this.clinicService.findMealsByAthleteId(athleteId));
		if (meal.isEmpty()){
			return new ResponseEntity<Collection<Meal>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Meal>>(meal, HttpStatus.OK);
	}


    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/{username}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Meal> addMeal(@RequestBody @Valid Meal meal, @PathVariable("username") String username, BindingResult bindingResult, UriComponentsBuilder ucBuilder){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (meal == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Meal>(headers, HttpStatus.BAD_REQUEST);
        }
        Athlete athlete = this.clinicService.findAthleteByUsername(username);
        meal.setAthlete(athlete);
		this.clinicService.saveMeal(meal);
		headers.setLocation(ucBuilder.path("/api/meals/{id}").buildAndExpand(meal.getId()).toUri());
		return new ResponseEntity<Meal>(meal, headers, HttpStatus.CREATED);
	}

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/{mealId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Meal> updateMeal(@PathVariable("mealId") int mealId, @RequestBody @Valid Meal meal, BindingResult bindingResult){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (meal == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Meal>(headers, HttpStatus.BAD_REQUEST);
		}
		Meal currentMeal = this.clinicService.findMealById(mealId);
		if(currentMeal == null){
			return new ResponseEntity<Meal>(HttpStatus.NOT_FOUND);
        }

        currentMeal.setMealName(meal.getMealName());
        currentMeal.setCalories(meal.getCalories());
        currentMeal.setFat(meal.getFat());
        currentMeal.setCarbohydrates(meal.getCarbohydrates());
        currentMeal.setProtein(meal.getProtein());
        currentMeal.setDate(meal.getDate());

		this.clinicService.saveMeal(currentMeal);
		return new ResponseEntity<Meal>(currentMeal, HttpStatus.NO_CONTENT);
	}

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/{mealId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	public ResponseEntity<Void> deleteMeal(@PathVariable("mealId") int mealId){
		Meal meal = this.clinicService.findMealById(mealId);
		if(meal == null){
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.clinicService.deleteMeal(meal);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
