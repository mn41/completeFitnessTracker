package org.springframework.samples.completefitnesstracker.rest;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;

import org.springframework.samples.completefitnesstracker.model.Exercise;
import org.springframework.samples.completefitnesstracker.model.Workout;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

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
