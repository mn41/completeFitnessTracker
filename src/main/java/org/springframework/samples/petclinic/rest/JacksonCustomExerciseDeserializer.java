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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.samples.petclinic.model.Exercise;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * @author Vitaliy Fedoriv
 *
 */

public class JacksonCustomExerciseDeserializer extends StdDeserializer<Exercise> {

	public JacksonCustomExerciseDeserializer() {
		this(null);
	}

	public JacksonCustomExerciseDeserializer(Class<Exercise> t) {
		super(t);
	}

	@Override
	public Exercise deserialize(JsonParser parser, DeserializationContext context)	throws IOException, JsonProcessingException {
        Exercise exercise = new Exercise();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        JsonNode node = parser.getCodec().readTree(parser);
        Date date;
        int exerciseId = node.get("id").asInt();

        if (!(exerciseId == -1)) {
			exercise.setId(exerciseId);
        }

        if(node.has("exerciseName")){
            String exerciseName = node.get("exerciseName").asText(null);
            exercise.setExerciseName(exerciseName);
        }

        if(node.has("weight")){
            double weight = node.get("weight").asDouble();
            exercise.setWeight(weight);
        }

        if(node.has("reps")){
            double reps = node.get("reps").asDouble();
            exercise.setReps(reps);
        }

        if(node.has("elapsedTime")){
            double elapsedTime = node.get("elapsedTime").asDouble();
            exercise.setElapsedTime(elapsedTime);
        }

        if(node.has("date")){
            String dateStr = node.get("date").asText(null);
            try {
                date = formatter.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
            exercise.setDate(date);
        }

        if(node.has("sequenceNumber")){
            int sequenceNumber = node.get("sequenceNumber").asInt();
            exercise.setSequenceNumber(sequenceNumber);
        }

		if (!(exerciseId == -1)) {
			exercise.setId(exerciseId);
        }

        return exercise;
    }

}
