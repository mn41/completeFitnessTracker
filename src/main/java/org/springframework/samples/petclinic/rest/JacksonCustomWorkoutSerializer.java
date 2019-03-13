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

import org.springframework.samples.petclinic.model.Exercise;
import org.springframework.samples.petclinic.model.Workout;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * @author Vitaliy Fedoriv
 *
 */

public class JacksonCustomWorkoutSerializer extends StdSerializer<Workout> {

	public JacksonCustomWorkoutSerializer() {
		this(null);
	}

	public JacksonCustomWorkoutSerializer(Class<Workout> t) {
		super(t);
	}


	@Override
	public void serialize(Workout workout, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		if (workout == null)  {
			throw new IOException("Cannot serialize Exercise object - exercise or exercise.workout is null");
        }
        Format formatter = new SimpleDateFormat("yyyy/MM/dd");
		jgen.writeStartObject();
		if (workout.getId() == null) {
			jgen.writeNullField("id");
		} else {
			jgen.writeNumberField("id", workout.getId());
        }
        jgen.writeStringField("workoutName", workout.getWorkoutName());
        jgen.writeStringField("category", workout.getCategory());
        jgen.writeStringField("date", formatter.format(workout.getDate()));

        jgen.writeArrayFieldStart("exercises");
		for (Exercise exercise : workout.getExercises()) {
			jgen.writeStartObject();
            if (exercise.getId() == null) {
                jgen.writeNullField("id");
            } else {
                jgen.writeNumberField("id", exercise.getId());
            }

            jgen.writeStringField("exerciseName", exercise.getExerciseName());
            jgen.writeNumberField("weight", exercise.getWeight());
            jgen.writeNumberField("reps", exercise.getReps());
            jgen.writeNumberField("elapsedTime", exercise.getElapsedTime());
            jgen.writeNumberField("sequenceNumber", exercise.getSequenceNumber());

            jgen.writeEndObject();
		}
        jgen.writeEndArray();
        jgen.writeEndObject();
	}

}
