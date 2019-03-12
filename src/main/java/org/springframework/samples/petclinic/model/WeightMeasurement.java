/*
 * Copyright 2002-2013 the original author or authors.
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
package org.springframework.samples.petclinic.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Models a {@link Vet Vet's} specialty (for example, dentistry).
 *
 * @author Juergen Hoeller
 */
@Entity
@Table(name = "weight_measurements")
public class WeightMeasurement extends BaseEntity {

    @Column(name = "weight")
    private double weight;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy/MM/dd")
    private Date date;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "athlete_id")
    private Athlete athlete;

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Athlete getAthlete() {
        return this.athlete;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }

    @Override
    public String toString() {
        return "{" +
            " weight='" + getWeight() + "'" +
            ", date='" + getDate() + "'" +
            ", athlete='" + getAthlete() + "'" +
            "}";
    }


}
