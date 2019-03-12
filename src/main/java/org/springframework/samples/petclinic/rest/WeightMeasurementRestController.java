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

package org.springframework.samples.petclinic.rest;


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
import org.springframework.samples.petclinic.model.Athlete;
import org.springframework.samples.petclinic.model.WeightMeasurement;
import org.springframework.samples.petclinic.service.ClinicService;
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

/**
 * @author Vitaliy Fedoriv
 *
 */

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/weightMeasurements")
public class WeightMeasurementRestController {

    @Autowired
    private ClinicService clinicService;

    @PreAuthorize("hasRole(@roles.VET_ADMIN)")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<WeightMeasurement>> getAllWeightMeasurements() {
        Collection<WeightMeasurement> weightMeasurements = new ArrayList<WeightMeasurement>();
        weightMeasurements.addAll(this.clinicService.findAllWeightMeasurements());
        if (weightMeasurements.isEmpty()) {
            return new ResponseEntity<Collection<WeightMeasurement>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<WeightMeasurement>>(weightMeasurements, HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@roles.VET_ADMIN)")
    @RequestMapping(value = "/{weightMeasurementId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<WeightMeasurement> getWeightMeasurement(
            @PathVariable("weightMeasurementId") int weightMeasurementId) {
        WeightMeasurement weightMeasurement = this.clinicService.findWeightMeasurementById(weightMeasurementId);
        if (weightMeasurement == null) {
            return new ResponseEntity<WeightMeasurement>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<WeightMeasurement>(weightMeasurement, HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@roles.VET_ADMIN)")
    @RequestMapping(value = "/dateBetween", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<WeightMeasurement>> getWeightMeasurementByDateRange(
            @RequestParam(name = "startDate") String startDateString, @RequestParam(name = "endDate") String endDateString, @RequestParam(name = "athleteId") int athleteId) {

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date startDate = null;
        Date endDate = null;
        try{
            startDate = df.parse(startDateString);
            endDate = df.parse(endDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Collection<WeightMeasurement> weightMeasurement = this.clinicService
                .findWeightMeasurementsByDateBetweenAndAthleteId(startDate, endDate, athleteId);
        if (weightMeasurement == null) {
            return new ResponseEntity<Collection<WeightMeasurement>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<WeightMeasurement>>(weightMeasurement, HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@roles.VET_ADMIN)")
    @RequestMapping(value = "/athlete/{athleteId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<WeightMeasurement>> getWeightMeasurementByAthleteId(
            @PathVariable("athleteId") int athleteId) {
        Collection<WeightMeasurement> weightMeasurement = new ArrayList<WeightMeasurement>();
        weightMeasurement.addAll(this.clinicService.findWeightMeasurementsByAthleteId(athleteId));
        if (weightMeasurement.isEmpty()) {
            return new ResponseEntity<Collection<WeightMeasurement>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<WeightMeasurement>>(weightMeasurement, HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@roles.VET_ADMIN)")
    @RequestMapping(value = "/add/{athleteId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<WeightMeasurement> addWeightMeasurement(
            @RequestBody @Valid WeightMeasurement weightMeasurement, @PathVariable("athleteId") int athleteId,
            BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (weightMeasurement == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<WeightMeasurement>(headers, HttpStatus.BAD_REQUEST);
        }
        Athlete athlete = this.clinicService.findAthleteById(athleteId);
        weightMeasurement.setAthlete(athlete);
        this.clinicService.saveWeightMeasurement(weightMeasurement);
        headers.setLocation(
                ucBuilder.path("/api/weightMeasurements/{id}").buildAndExpand(weightMeasurement.getId()).toUri());
        return new ResponseEntity<WeightMeasurement>(weightMeasurement, headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole(@roles.VET_ADMIN)")
    @RequestMapping(value = "/{weightMeasurementId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<WeightMeasurement> updateWeightMeasurement(
            @PathVariable("weightMeasurementId") int weightMeasurementId,
            @RequestBody @Valid WeightMeasurement weightMeasurement, BindingResult bindingResult) {
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if (bindingResult.hasErrors() || (weightMeasurement == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<WeightMeasurement>(headers, HttpStatus.BAD_REQUEST);
        }
        WeightMeasurement currentWeightMeasurement = this.clinicService.findWeightMeasurementById(weightMeasurementId);
        if (currentWeightMeasurement == null) {
            return new ResponseEntity<WeightMeasurement>(HttpStatus.NOT_FOUND);
        }
        currentWeightMeasurement.setWeight(weightMeasurement.getWeight());
        currentWeightMeasurement.setDate(weightMeasurement.getDate());
        this.clinicService.saveWeightMeasurement(currentWeightMeasurement);
        return new ResponseEntity<WeightMeasurement>(currentWeightMeasurement, HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole(@roles.VET_ADMIN)")
    @RequestMapping(value = "/{weightMeasurementId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional
    public ResponseEntity<Void> deleteWeightMeasurement(@PathVariable("weightMeasurementId") int weightMeasurementId) {
        WeightMeasurement weightMeasurement = this.clinicService.findWeightMeasurementById(weightMeasurementId);
        if (weightMeasurement == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        this.clinicService.deleteWeightMeasurement(weightMeasurement);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
