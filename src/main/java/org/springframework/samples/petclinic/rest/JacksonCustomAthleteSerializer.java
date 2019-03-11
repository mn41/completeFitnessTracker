/*
 * Copyright 2016 the original author or authors.
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

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;

import org.springframework.samples.petclinic.model.Athlete;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.WeightMeasurement;
import org.springframework.samples.petclinic.model.Food;
import org.springframework.samples.petclinic.model.Meal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * @author Vitaliy Fedoriv
 *
 */

public class JacksonCustomAthleteSerializer extends StdSerializer<Athlete> {

    public JacksonCustomAthleteSerializer() {
        this(null);
    }

    public JacksonCustomAthleteSerializer(Class<Athlete> t) {
        super(t);
    }

    @Override
    public void serialize(Athlete athlete, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (athlete == null) {
            throw new IOException("Cannot serialize Food object - food or food.athlete is null");
        }

        jgen.writeStartObject();
        if (athlete.getId() == null) {
            jgen.writeNullField("id");
        } else {
            jgen.writeNumberField("id", athlete.getId());
        }

        jgen.writeStringField("username", athlete.getUsername());
        jgen.writeStringField("password", athlete.getPassword());
        jgen.writeStringField("email", athlete.getEmail());


        jgen.writeEndObject();
    }

}
