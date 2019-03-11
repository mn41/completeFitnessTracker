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
import org.springframework.samples.petclinic.model.Exercise;
import org.springframework.samples.petclinic.model.Workout;
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
@RequestMapping("api/workout")
public class WorkoutRestController {

    @Autowired
	private ClinicService clinicService;

	@PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Workout>> getAllWorkouts(){
		Collection<Workout> workout = new ArrayList<Workout>();
		workout.addAll(this.clinicService.findAllWorkouts());
		if (workout.isEmpty()){
			return new ResponseEntity<Collection<Workout>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Workout>>(workout, HttpStatus.OK);
    }

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/{workoutId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Workout> getWorkout(@PathVariable("workoutId") int workoutId){
		Workout workout = this.clinicService.findWorkoutById(workoutId);
		if(workout == null){
			return new ResponseEntity<Workout>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Workout>(workout, HttpStatus.OK);
    }

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/athlete/{athleteId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Workout>> getWorkoutByAthleteId(@PathVariable("athleteId") int athleteId){
		Collection<Workout> workout = new ArrayList<Workout>();
		workout.addAll(this.clinicService.findWorkoutsByAthleteId(athleteId));
		if (workout.isEmpty()){
			return new ResponseEntity<Collection<Workout>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Workout>>(workout, HttpStatus.OK);
	}


    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/{username}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Workout> addWorkout(@RequestBody @Valid Workout workout, @PathVariable("username") String username, BindingResult bindingResult, UriComponentsBuilder ucBuilder){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (workout == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Workout>(headers, HttpStatus.BAD_REQUEST);
        }
        Athlete athlete = this.clinicService.findAthleteByUsername(username);
        workout.setAthlete(athlete);
		this.clinicService.saveWorkout(workout);
		headers.setLocation(ucBuilder.path("/api/workouts/{id}").buildAndExpand(workout.getId()).toUri());
		return new ResponseEntity<Workout>(workout, headers, HttpStatus.CREATED);
	}

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/{workoutId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Workout> updateWorkout(@PathVariable("workoutId") int workoutId, @RequestBody @Valid Workout workout, BindingResult bindingResult){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (workout == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Workout>(headers, HttpStatus.BAD_REQUEST);
		}
		Workout currentWorkout = this.clinicService.findWorkoutById(workoutId);
		if(currentWorkout == null){
			return new ResponseEntity<Workout>(HttpStatus.NOT_FOUND);
        }

        currentWorkout.setWorkoutName(workout.getWorkoutName());
        currentWorkout.setWorkoutCategory(workout.getWorkoutCategory());
        currentWorkout.setDate(workout.getDate());

		this.clinicService.saveWorkout(currentWorkout);
		return new ResponseEntity<Workout>(currentWorkout, HttpStatus.NO_CONTENT);
	}

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/{workoutId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	public ResponseEntity<Void> deleteWorkout(@PathVariable("workoutId") int workoutId){
		Workout workout = this.clinicService.findWorkoutById(workoutId);
		if(workout == null){
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.clinicService.deleteWorkout(workout);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
