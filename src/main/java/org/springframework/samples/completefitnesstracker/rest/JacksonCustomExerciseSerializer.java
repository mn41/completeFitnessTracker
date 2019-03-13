package org.springframework.samples.completefitnesstracker.rest;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;

import org.springframework.samples.completefitnesstracker.model.Exercise;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class JacksonCustomExerciseSerializer extends StdSerializer<Exercise> {

	public JacksonCustomExerciseSerializer() {
		this(null);
	}

	public JacksonCustomExerciseSerializer(Class<Exercise> t) {
		super(t);
	}


	@Override
	public void serialize(Exercise exercise, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		if (exercise == null)  {
			throw new IOException("Cannot serialize Exercise object - exercise or exercise.pet is null");
        }

        Format formatter = new SimpleDateFormat("yyyy/MM/dd");

		jgen.writeStartObject();
		if (exercise.getId() == null) {
			jgen.writeNullField("id");
		} else {
			jgen.writeNumberField("id", exercise.getId());
        }



        jgen.writeStringField("exerciseName", exercise.getExerciseName());
        jgen.writeNumberField("weight", exercise.getWeight());
        jgen.writeNumberField("reps", exercise.getReps());
        jgen.writeNumberField("sets", exercise.getSequenceNumber());
        jgen.writeNumberField("elapsedTime", exercise.getElapsedTime());
        jgen.writeStringField("date", formatter.format(exercise.getDate()));
        jgen.writeNumberField("sequenceNumber", exercise.getSequenceNumber());


		jgen.writeEndObject();
	}

}
