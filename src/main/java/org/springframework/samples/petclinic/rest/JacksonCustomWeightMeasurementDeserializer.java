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

import org.springframework.samples.petclinic.model.WeightMeasurement;
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

public class JacksonCustomWeightMeasurementDeserializer extends StdDeserializer<WeightMeasurement> {

	public JacksonCustomWeightMeasurementDeserializer() {
		this(null);
	}

	public JacksonCustomWeightMeasurementDeserializer(Class<WeightMeasurement> t) {
		super(t);
	}

	@Override
	public WeightMeasurement deserialize(JsonParser parser, DeserializationContext context)	throws IOException, JsonProcessingException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        WeightMeasurement weightMeasurement = new WeightMeasurement();
        Athlete athlete = new Athlete();
        Date date = null;
		ObjectMapper mapper = new ObjectMapper();
        JsonNode node = parser.getCodec().readTree(parser);
        JsonNode athlete_node = node.get("athlete");
		athlete = mapper.treeToValue(athlete_node, Athlete.class);
        int weightMeasurementId = node.get("id").asInt();
        double weight = node.get("weight").asDouble();
        String dateStr = node.get("date").asText(null);
		try {
			date = formatter.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new IOException(e);
		}

		if (!(weightMeasurementId == -1)) {
			weightMeasurement.setId(weightMeasurementId);
        }
        weightMeasurement.setWeight(weight);
        weightMeasurement.setDate(date);
        weightMeasurement.setAthlete(athlete);
		return weightMeasurement;
	}

}
