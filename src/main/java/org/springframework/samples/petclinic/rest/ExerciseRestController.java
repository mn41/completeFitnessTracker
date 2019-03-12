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

import org.springframework.samples.petclinic.model.Workout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Exercise;
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
@RequestMapping("api/exercise")
public class ExerciseRestController {

    @Autowired
	private ClinicService clinicService;

	@PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Exercise>> getAllExercises(){
		Collection<Exercise> exercise = new ArrayList<Exercise>();
		exercise.addAll(this.clinicService.findAllExercises());
		if (exercise.isEmpty()){
			return new ResponseEntity<Collection<Exercise>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Exercise>>(exercise, HttpStatus.OK);
    }

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/search/workout/{workoutId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Exercise>> getAllExercisesByWorkoutId(@PathVariable("workoutId") int workoutId){
		Collection<Exercise> exercise = new ArrayList<Exercise>();
		exercise.addAll(this.clinicService.findExercisesByWorkoutId(workoutId));
		if (exercise.isEmpty()){
			return new ResponseEntity<Collection<Exercise>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Exercise>>(exercise, HttpStatus.OK);
	}

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/{exerciseId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Exercise> getExercise(@PathVariable("exerciseId") int exerciseId){
		Exercise exercise = this.clinicService.findExerciseById(exerciseId);
		if(exercise == null){
			return new ResponseEntity<Exercise>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Exercise>(exercise, HttpStatus.OK);
	}

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/{workoutId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Exercise> addExercise(@PathVariable("workoutId") int workoutId, @RequestBody @Valid Exercise exercise, BindingResult bindingResult, UriComponentsBuilder ucBuilder){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (exercise == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Exercise>(headers, HttpStatus.BAD_REQUEST);
        }
        Workout workout = this.clinicService.findWorkoutById(workoutId);
        exercise.setWorkout(workout);
		this.clinicService.saveExercise(exercise);
		headers.setLocation(ucBuilder.path("/api/exercises/{id}").buildAndExpand(exercise.getId()).toUri());
		return new ResponseEntity<Exercise>(exercise, headers, HttpStatus.CREATED);
	}

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/{exerciseId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Exercise> updateExercise(@PathVariable("exerciseId") int exerciseId, @RequestBody @Valid Exercise exercise, BindingResult bindingResult){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (exercise == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Exercise>(headers, HttpStatus.BAD_REQUEST);
		}
		Exercise currentExercise = this.clinicService.findExerciseById(exerciseId);
		if(currentExercise == null){
			return new ResponseEntity<Exercise>(HttpStatus.NOT_FOUND);
        }

        currentExercise.setExerciseName(exercise.getExerciseName());
        currentExercise.setWeight(exercise.getWeight());
        currentExercise.setReps(exercise.getReps());
        currentExercise.setElapsedTime(exercise.getElapsedTime());
        currentExercise.setSequenceNumber(exercise.getSequenceNumber());

		this.clinicService.saveExercise(currentExercise);
		return new ResponseEntity<Exercise>(currentExercise, HttpStatus.NO_CONTENT);
	}

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/{exerciseId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	public ResponseEntity<Void> deleteExercise(@PathVariable("exerciseId") int exerciseId){
		Exercise exercise = this.clinicService.findExerciseById(exerciseId);
		if(exercise == null){
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.clinicService.deleteExercise(exercise);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
