package org.springframework.samples.completefitnesstracker.rest;

import java.io.IOException;

import org.springframework.samples.completefitnesstracker.model.Athlete;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class JacksonCustomAthleteDeserializer extends StdDeserializer<Athlete> {

	public JacksonCustomAthleteDeserializer() {
		this(null);
	}

	public JacksonCustomAthleteDeserializer(Class<Athlete> t) {
		super(t);
	}

	@Override
	public Athlete deserialize(JsonParser parser, DeserializationContext context)	throws IOException, JsonProcessingException {
        Athlete athlete = new Athlete();
        JsonNode node = parser.getCodec().readTree(parser);
        int athleteId = node.get("id").asInt();
        String username = node.get("username").asText();
        String password = node.get("password").asText();
        String email = node.get("email").asText();

		if (!(athleteId == -1)) {
			athlete.setId(athleteId);
        }

        athlete.setId(athleteId);
        athlete.setUsername(username);
        athlete.setPassword(password);
        athlete.setEmail(email);

		return athlete;
	}

}
