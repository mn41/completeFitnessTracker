package org.springframework.samples.completefitnesstracker.rest;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.completefitnesstracker.model.Food;
import org.springframework.samples.completefitnesstracker.model.Meal;
import org.springframework.samples.completefitnesstracker.service.TrackerService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/food")
public class FoodRestController {

    @Autowired
	private TrackerService trackerService;

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Food>> getAllFoods(){
		Collection<Food> food = new ArrayList<Food>();
		food.addAll(this.trackerService.findAllFoods());
		if (food.isEmpty()){
			return new ResponseEntity<Collection<Food>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Food>>(food, HttpStatus.OK);
    }

	@RequestMapping(value = "/search/meal/{mealId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Food>> getFoodsByMealId(@PathVariable("mealId") int mealId){
		Collection<Food> food = new ArrayList<Food>();
		food.addAll(this.trackerService.findFoodsByMealId(mealId));
		if (food.isEmpty()){
			return new ResponseEntity<Collection<Food>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Food>>(food, HttpStatus.OK);
	}

	@RequestMapping(value = "/{foodId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Food> getFood(@PathVariable("foodId") int foodId){
		Food food = this.trackerService.findFoodById(foodId);
		if(food == null){
			return new ResponseEntity<Food>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Food>(food, HttpStatus.OK);
	}

	@RequestMapping(value = "/add/{mealId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Food> addFood(@PathVariable("mealId") int mealId, @RequestBody @Valid Food food, BindingResult bindingResult, UriComponentsBuilder ucBuilder){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (food == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Food>(headers, HttpStatus.BAD_REQUEST);
        }
        Meal meal = this.trackerService.findMealById(mealId);
        food.setMeal(meal);
        this.trackerService.saveFood(food);
        meal.updateTotals();
		headers.setLocation(ucBuilder.path("/api/foods/{id}").buildAndExpand(food.getId()).toUri());
		return new ResponseEntity<Food>(food, headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{foodId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Food> updateFood(@PathVariable("foodId") int foodId, @RequestBody @Valid Food food, BindingResult bindingResult){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (food == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Food>(headers, HttpStatus.BAD_REQUEST);
		}
		Food currentFood = this.trackerService.findFoodById(foodId);
		if(currentFood == null){
			return new ResponseEntity<Food>(HttpStatus.NOT_FOUND);
        }

        currentFood.setFoodName(food.getFoodName());
        currentFood.setCalories(food.getCalories());
        currentFood.setFat(food.getFat());
        currentFood.setCarbohydrates(food.getCarbohydrates());
        currentFood.setProtein(food.getProtein());
        currentFood.setServings(food.getServings());

		this.trackerService.saveFood(currentFood);
		return new ResponseEntity<Food>(currentFood, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{foodId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	public ResponseEntity<Void> deleteFood(@PathVariable("foodId") int foodId){
		Food food = this.trackerService.findFoodById(foodId);
		if(food == null){
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.trackerService.deleteFood(food);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
