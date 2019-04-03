package org.springframework.samples.completefitnesstracker.rest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.completefitnesstracker.model.Athlete;
import org.springframework.samples.completefitnesstracker.model.Exercise;
import org.springframework.samples.completefitnesstracker.model.Workout;
import org.springframework.samples.completefitnesstracker.service.TrackerService;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("api/workout")
public class WorkoutRestController {

    @Autowired
	private TrackerService trackerService;

	@PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Workout>> getAllWorkouts(){
		Collection<Workout> workout = new ArrayList<Workout>();
		workout.addAll(this.trackerService.findAllWorkouts());
		if (workout.isEmpty()){
			return new ResponseEntity<Collection<Workout>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Workout>>(workout, HttpStatus.OK);
    }

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/{workoutId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Workout> getWorkout(@PathVariable("workoutId") int workoutId){
		Workout workout = this.trackerService.findWorkoutById(workoutId);
		if(workout == null){
			return new ResponseEntity<Workout>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Workout>(workout, HttpStatus.OK);
    }

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/athlete/{athleteId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Workout>> getWorkoutByAthleteId(@PathVariable("athleteId") int athleteId){
		Collection<Workout> workout = new ArrayList<Workout>();
		workout.addAll(this.trackerService.findWorkoutsByAthleteId(athleteId));
		if (workout.isEmpty()){
			return new ResponseEntity<Collection<Workout>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Workout>>(workout, HttpStatus.OK);
    }

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/recent/athlete/{athleteId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Workout>> getRecentWorkoutByAthleteId(@PathVariable("athleteId") int athleteId, @RequestParam(name = "category") String category){
		Collection<Workout> workout = new ArrayList<Workout>();
		workout.addAll(this.trackerService.findRecentWorkoutByAthleteIdAndCategory(athleteId, category));
		if (workout.isEmpty()){
			return new ResponseEntity<Collection<Workout>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Workout>>(workout, HttpStatus.OK);
    }


    @PreAuthorize("hasRole(@roles.VET_ADMIN)")
    @RequestMapping(value = "/dateBetween", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<Workout>> getWorkoutByDateRange(
            @RequestParam(name = "startDate") String startDateString, @RequestParam(name = "endDate") String endDateString, @RequestParam(name = "athleteId") int athleteId, @RequestParam(name = "category") String category) {

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date startDate = null;
        Date endDate = null;
        try{
            startDate = df.parse(startDateString);
            endDate = df.parse(endDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Collection<Workout> workout = this.trackerService
                .findWorkoutsByDateBetweenAndAthleteIdAndCategory(startDate, endDate, athleteId, category);
        if (workout == null) {
            return new ResponseEntity<Collection<Workout>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<Workout>>(workout, HttpStatus.OK);
    }


    @PreAuthorize("hasRole(@roles.VET_ADMIN)")
    @RequestMapping(value = "/add/{athleteId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Workout> addWorkout(
            @RequestBody @Valid Workout workout, @PathVariable("athleteId") int athleteId,
            BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (workout == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<Workout>(headers, HttpStatus.BAD_REQUEST);
        }

        Collection<Workout> dateOfWorkout = this.trackerService
                .findWorkoutsByDateBetweenAndAthleteIdAndCategory(workout.getDate(), workout.getDate(), athleteId, workout.getCategory());
        if (dateOfWorkout.isEmpty()){
            Athlete athlete = this.trackerService.findAthleteById(athleteId);
            workout.setAthlete(athlete);
            this.trackerService.saveWorkout(workout);

            headers.setLocation(
                    ucBuilder.path("/api/workouts/{id}").buildAndExpand(workout.getId()).toUri());
        } else {
            Workout currentWorkout = dateOfWorkout.iterator().next();
            for (Exercise exercise : workout.getExercises()){
               currentWorkout.addExercise(exercise);
            }
            this.trackerService.saveWorkout(currentWorkout);
        }
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
		Workout currentWorkout = this.trackerService.findWorkoutById(workoutId);
		if(currentWorkout == null){
			return new ResponseEntity<Workout>(HttpStatus.NOT_FOUND);
        }
        workout.setAthlete(currentWorkout.getAthlete());
        this.trackerService.deleteWorkout(currentWorkout);
		this.trackerService.saveWorkout(workout);
		return new ResponseEntity<Workout>(workout, HttpStatus.NO_CONTENT);
	}

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/{workoutId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	public ResponseEntity<Void> deleteWorkout(@PathVariable("workoutId") int workoutId){
		Workout workout = this.trackerService.findWorkoutById(workoutId);
		if(workout == null){
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.trackerService.deleteWorkout(workout);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
