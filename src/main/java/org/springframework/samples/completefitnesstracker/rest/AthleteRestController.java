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
import org.springframework.samples.completefitnesstracker.model.Athlete;
import org.springframework.samples.completefitnesstracker.service.TrackerService;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("api/athlete")
public class AthleteRestController {

    @Autowired
	private TrackerService trackerService;

	@PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Athlete>> getAllAthletes(){
		Collection<Athlete> athlete = new ArrayList<Athlete>();
		athlete.addAll(this.trackerService.findAllAthletes());
		if (athlete.isEmpty()){
			return new ResponseEntity<Collection<Athlete>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Athlete>>(athlete, HttpStatus.OK);
    }

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/{athleteId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Athlete> getAthleteByUsername(@PathVariable("athleteId") int athleteId){
		Athlete athlete = this.trackerService.findAthleteById(athleteId);
		if(athlete == null){
			return new ResponseEntity<Athlete>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Athlete>(athlete, HttpStatus.OK);
	}

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/login/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Athlete> getAthleteByUsername(@PathVariable("username") String username){
		Athlete athlete = this.trackerService.findAthleteByUsername(username);
		if(athlete == null){
			return new ResponseEntity<Athlete>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Athlete>(athlete, HttpStatus.OK);
	}

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Athlete> addAthlete(@RequestBody @Valid Athlete athlete, BindingResult bindingResult, UriComponentsBuilder ucBuilder){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (athlete == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Athlete>(headers, HttpStatus.BAD_REQUEST);
		}
		this.trackerService.saveAthlete(athlete);
		headers.setLocation(ucBuilder.path("/api/athletes/{id}").buildAndExpand(athlete.getId()).toUri());
		return new ResponseEntity<Athlete>(athlete, headers, HttpStatus.CREATED);
    }

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Athlete> athleteLogin(@RequestBody @Valid Athlete athlete, BindingResult bindingResult, UriComponentsBuilder ucBuilder){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (athlete == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Athlete>(headers, HttpStatus.BAD_REQUEST);
        }


        if (this.trackerService.athleteExistsByUsername(athlete.getUsername())){
            Athlete currentAthlete = this.trackerService.findAthleteByUsername(athlete.getUsername());
            if(currentAthlete.getPassword().equals(athlete.getPassword())){
                return new ResponseEntity<Athlete>(currentAthlete, headers, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<Athlete>(athlete, HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<Athlete>(athlete, HttpStatus.BAD_REQUEST);
        }
	}

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/{athleteId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Athlete> updateAthlete(@PathVariable("athleteId") int athleteId, @RequestBody @Valid Athlete athlete, BindingResult bindingResult){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (athlete == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Athlete>(headers, HttpStatus.BAD_REQUEST);
		}
		Athlete currentAthlete = this.trackerService.findAthleteById(athleteId);
		if(currentAthlete == null){
			return new ResponseEntity<Athlete>(HttpStatus.NOT_FOUND);
        }

        currentAthlete.setUsername(athlete.getUsername());
        currentAthlete.setPassword(athlete.getPassword());
        currentAthlete.setEmail(athlete.getEmail());

		this.trackerService.saveAthlete(currentAthlete);
		return new ResponseEntity<Athlete>(currentAthlete, HttpStatus.NO_CONTENT);
	}

    @PreAuthorize( "hasRole(@roles.VET_ADMIN)" )
	@RequestMapping(value = "/{athleteId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	public ResponseEntity<Void> deleteAthlete(@PathVariable("athleteId") int athleteId){
		Athlete athlete = this.trackerService.findAthleteById(athleteId);
		if(athlete == null){
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.trackerService.deleteAthlete(athlete);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
