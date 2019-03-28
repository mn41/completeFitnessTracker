package org.springframework.samples.completefitnesstracker.rest;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.samples.completefitnesstracker.model.Workout;
import org.springframework.samples.completefitnesstracker.model.Exercise;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class JacksonCustomWorkoutDeserializer extends StdDeserializer<Workout> {

	public JacksonCustomWorkoutDeserializer() {
		this(null);
	}

	public JacksonCustomWorkoutDeserializer(Class<Workout> t) {
		super(t);
	}

	@Override
	public Workout deserialize(JsonParser parser, DeserializationContext context)	throws IOException, JsonProcessingException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Workout workout = new Workout();
        ObjectMapper mapper = new ObjectMapper();
        Date date = null;
        JsonNode node = parser.getCodec().readTree(parser);
        String dateStr = node.get("date").asText(null);

		try {
			date = formatter.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new IOException(e);
        }

        workout.setDate(date);

        if(node.has("id")) {
            int workoutId = node.get("id").asInt();
            if (!(workoutId == -1)) {
                workout.setId(workoutId);
            }
        }
        if(node.has("workoutName")) {
            String workoutName = node.get("workoutName").asText(null);
            workout.setWorkoutName(workoutName);
        }

        if(node.has("category")) {
            String category = node.get("category").asText(null);
            workout.setCategory(category);
        }


        if (node.has("exercises")) {
            JsonNode exercises_node = node.get("exercises");
            Exercise[] exercises = mapper.treeToValue(exercises_node, Exercise[].class);
            for (Exercise exercise : exercises){
                workout.addExercise(exercise);
            }
        }

		return workout;
	}

}
