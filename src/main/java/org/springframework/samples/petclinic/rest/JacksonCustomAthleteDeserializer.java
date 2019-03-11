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

import org.springframework.samples.petclinic.model.Athlete;

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

		if (!(athleteId == 0)) {
			athlete.setId(athleteId);
        }

        athlete.setId(athleteId);
        athlete.setUsername(username);
        athlete.setPassword(password);
        athlete.setEmail(email);

		return athlete;
	}

}
