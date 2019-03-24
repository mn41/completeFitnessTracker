package org.springframework.samples.completefitnesstracker.rest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.samples.completefitnesstracker.model.Workout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.completefitnesstracker.model.Exercise;
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
@RequestMapping("api/exercise")
public class ExerciseRestController {

    @Autowired
	private TrackerService trackerService;

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Exercise>> getAllExercises(){
		Collection<Exercise> exercise = new ArrayList<Exercise>();
		exercise.addAll(this.trackerService.findAllExercises());
		if (exercise.isEmpty()){
			return new ResponseEntity<Collection<Exercise>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Exercise>>(exercise, HttpStatus.OK);
    }

	@RequestMapping(value = "/search/workout/{workoutId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Exercise>> getAllExercisesByWorkoutId(@PathVariable("workoutId") int workoutId){
		Collection<Exercise> exercise = new ArrayList<Exercise>();
		exercise.addAll(this.trackerService.findExercisesByWorkoutId(workoutId));
		if (exercise.isEmpty()){
			return new ResponseEntity<Collection<Exercise>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Exercise>>(exercise, HttpStatus.OK);
	}

	@RequestMapping(value = "/{exerciseId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Exercise> getExercise(@PathVariable("exerciseId") int exerciseId){
		Exercise exercise = this.trackerService.findExerciseById(exerciseId);
		if(exercise == null){
			return new ResponseEntity<Exercise>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Exercise>(exercise, HttpStatus.OK);
    }

    @RequestMapping(value = "/dateBetween", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<Exercise>> getExerciseByDateRange(
            @RequestParam(name = "startDate") String startDateString, @RequestParam(name = "endDate") String endDateString, @RequestParam(name = "workoutId") int workoutId) {

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date startDate = null;
        Date endDate = null;
        try{
            startDate = df.parse(startDateString);
            endDate = df.parse(endDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Collection<Exercise> exercise = this.trackerService
                .findExercisesByDateBetweenAndWorkoutId(startDate, endDate, workoutId);
        if (exercise == null) {
            return new ResponseEntity<Collection<Exercise>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Exercise>>(exercise, HttpStatus.OK);
    }

    @RequestMapping(value = "/dateBetweenCategory", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<Exercise>> getExerciseByDateRangeAndCategory(
            @RequestParam(name = "startDate") String startDateString, @RequestParam(name = "endDate") String endDateString, @RequestParam(name = "exerciseName") String exerciseName, @RequestParam(name = "workoutId") int workoutId) {

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date startDate = null;
        Date endDate = null;
        try{
            startDate = df.parse(startDateString);
            endDate = df.parse(endDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Collection<Exercise> exercise = this.trackerService
                .findExercisesByDateBetweenAndExerciseNameAndWorkoutId(startDate, endDate, exerciseName, workoutId);
        if (exercise == null) {
            return new ResponseEntity<Collection<Exercise>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Exercise>>(exercise, HttpStatus.OK);
    }

	@RequestMapping(value = "/add/{workoutId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Exercise> addExercise(@PathVariable("workoutId") int workoutId, @RequestBody @Valid Exercise exercise, BindingResult bindingResult, UriComponentsBuilder ucBuilder){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (exercise == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Exercise>(headers, HttpStatus.BAD_REQUEST);
        }
        Workout workout = this.trackerService.findWorkoutById(workoutId);
        exercise.setWorkout(workout);
		this.trackerService.saveExercise(exercise);
		headers.setLocation(ucBuilder.path("/api/exercises/{id}").buildAndExpand(exercise.getId()).toUri());
		return new ResponseEntity<Exercise>(exercise, headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{exerciseId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Exercise> updateExercise(@PathVariable("exerciseId") int exerciseId, @RequestBody @Valid Exercise exercise, BindingResult bindingResult){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (exercise == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Exercise>(headers, HttpStatus.BAD_REQUEST);
		}
		Exercise currentExercise = this.trackerService.findExerciseById(exerciseId);
		if(currentExercise == null){
			return new ResponseEntity<Exercise>(HttpStatus.NOT_FOUND);
        }

        currentExercise.setExerciseName(exercise.getExerciseName());
        currentExercise.setWeight(exercise.getWeight());
        currentExercise.setReps(exercise.getReps());
        currentExercise.setElapsedTime(exercise.getElapsedTime());
        currentExercise.setSequenceNumber(exercise.getSequenceNumber());

		this.trackerService.saveExercise(currentExercise);
		return new ResponseEntity<Exercise>(currentExercise, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{exerciseId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	public ResponseEntity<Void> deleteExercise(@PathVariable("exerciseId") int exerciseId){
		Exercise exercise = this.trackerService.findExerciseById(exerciseId);
		if(exercise == null){
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.trackerService.deleteExercise(exercise);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
