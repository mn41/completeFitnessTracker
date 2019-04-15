package org.springframework.samples.completefitnesstracker.rest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.completefitnesstracker.model.Athlete;
import org.springframework.samples.completefitnesstracker.model.Food;
import org.springframework.samples.completefitnesstracker.model.Meal;
import org.springframework.samples.completefitnesstracker.service.TrackerService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/meal")
public class MealRestController {

    @Autowired
    private TrackerService trackerService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<Meal>> getAllMeals() {
        Collection<Meal> meal = new ArrayList<Meal>();
        meal.addAll(this.trackerService.findAllMeals());
        if (meal.isEmpty()) {
            return new ResponseEntity<Collection<Meal>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Meal>>(meal, HttpStatus.OK);
    }

    @RequestMapping(value = "/{mealId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Meal> getMeal(@PathVariable("mealId") int mealId) {
        Meal meal = this.trackerService.findMealById(mealId);
        if (meal == null) {
            return new ResponseEntity<Meal>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Meal>(meal, HttpStatus.OK);
    }

    @RequestMapping(value = "/athlete/{athleteId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<Meal>> getMealByAthleteId(@PathVariable("athleteId") int athleteId) {
        Collection<Meal> meal = new ArrayList<Meal>();
        meal.addAll(this.trackerService.findMealsByAthleteId(athleteId));
        if (meal.isEmpty()) {
            return new ResponseEntity<Collection<Meal>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Meal>>(meal, HttpStatus.OK);
    }

    @RequestMapping(value = "/dateBetween", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<Meal>> getMealByDateRange(@RequestParam(name = "startDate") String startDateString,
            @RequestParam(name = "endDate") String endDateString, @RequestParam(name = "athleteId") int athleteId) {

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = df.parse(startDateString);
            endDate = df.parse(endDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Collection<Meal> meal = this.trackerService.findMealsByDateBetweenAndAthleteId(startDate, endDate, athleteId);
        if (meal == null) {
            return new ResponseEntity<Collection<Meal>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Meal>>(meal, HttpStatus.OK);
    }

    @RequestMapping(value = "/recent/athlete/{athleteId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<Meal>> getRecentMealsByAthleteId(@PathVariable("athleteId") int athleteId) {
        Collection<Meal> meal = new ArrayList<Meal>();
        meal.addAll(this.trackerService.findRecentMealByAthleteId(athleteId));
        if (meal.isEmpty()) {
            return new ResponseEntity<Collection<Meal>>(HttpStatus.NOT_FOUND);
        }
        Date mostRecentDate = meal.iterator().next().getDate();
        Collection<Meal> meals = this.trackerService.findMealsByDateBetweenAndAthleteId(mostRecentDate,  mostRecentDate, athleteId);
        return new ResponseEntity<Collection<Meal>>(meals, HttpStatus.OK);
    }

    @RequestMapping(value = "/add/{athleteId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Meal> addMeal(@RequestBody @Valid Meal meal, @PathVariable("athleteId") int athleteId,
            BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (meal == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Meal>(headers, HttpStatus.BAD_REQUEST);
        }

        Collection<Meal> dateOfMeal = this.trackerService.findMealsByDateBetweenAndAthleteId(meal.getDate(),
                meal.getDate(), athleteId);

        if (dateOfMeal.isEmpty()) {
            Athlete athlete = this.trackerService.findAthleteById(athleteId);
            meal.setAthlete(athlete);
            this.trackerService.saveMeal(meal);

            headers.setLocation(ucBuilder.path("/api/meals/{id}").buildAndExpand(meal.getId()).toUri());
        } else {
            Iterator<Meal> iterator = dateOfMeal.iterator();

            boolean mealFound = false;
            while (iterator.hasNext()) {
                if(iterator.next().getMealName() == meal.getMealName()){
                    Meal currentMeal = dateOfMeal.iterator().next();
                    for (Food food : meal.getFoods()) {
                        currentMeal.addFood(food);
                    }
                    this.trackerService.saveMeal(currentMeal);
                    meal = currentMeal;
                    mealFound = true;
                    break;
                }
            }

            if(!mealFound){
                Athlete athlete = this.trackerService.findAthleteById(athleteId);
                meal.setAthlete(athlete);
                this.trackerService.saveMeal(meal);

                headers.setLocation(ucBuilder.path("/api/meals/{id}").buildAndExpand(meal.getId()).toUri());
            }


        }
        return new ResponseEntity<Meal>(meal, headers, HttpStatus.CREATED);

    }

    @RequestMapping(value = "/{mealId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Meal> updateMeal(@PathVariable("mealId") int mealId, @RequestBody @Valid Meal meal,
            BindingResult bindingResult) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (meal == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Meal>(headers, HttpStatus.BAD_REQUEST);
        }
        Meal currentMeal = this.trackerService.findMealById(mealId);
        if (currentMeal == null) {
            return new ResponseEntity<Meal>(HttpStatus.NOT_FOUND);
        }
        /*
        currentMeal.setMealName(meal.getMealName());
        currentMeal.setCalories(meal.getCalories());
        currentMeal.setFat(meal.getFat());
        currentMeal.setCarbohydrates(meal.getCarbohydrates());
        currentMeal.setProtein(meal.getProtein());
        currentMeal.setDate(meal.getDate());
        */

        meal.setAthlete(currentMeal.getAthlete());
        this.trackerService.deleteMeal(currentMeal);
        this.trackerService.saveMeal(meal);
        return new ResponseEntity<Meal>(meal, HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{mealId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional
    public ResponseEntity<Void> deleteMeal(@PathVariable("mealId") int mealId) {
        Meal meal = this.trackerService.findMealById(mealId);
        if (meal == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        this.trackerService.deleteMeal(meal);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
