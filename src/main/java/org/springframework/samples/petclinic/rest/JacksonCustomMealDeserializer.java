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
import org.springframework.samples.petclinic.model.Food;
import org.springframework.samples.petclinic.model.Meal;

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

public class JacksonCustomMealDeserializer extends StdDeserializer<Meal> {

	public JacksonCustomMealDeserializer() {
		this(null);
	}

	public JacksonCustomMealDeserializer(Class<Meal> t) {
		super(t);
	}

	@Override
	public Meal deserialize(JsonParser parser, DeserializationContext context)	throws IOException, JsonProcessingException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        ObjectMapper mapper = new ObjectMapper();
        Meal meal = new Meal();
        Date date = null;
        JsonNode node = parser.getCodec().readTree(parser);
        int mealId = node.get("id").asInt();
        String mealName = node.get("mealName").asText(null);
		double calories = node.get("calories").asDouble();
        double fat = node.get("fat").asDouble();
        double carbohydrates = node.get("carbohydrates").asDouble();
        double protein = node.get("protein").asDouble();
        String dateStr = node.get("date").asText(null);
		try {
			date = formatter.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new IOException(e);
		}

		if (!(mealId == -1)) {
			meal.setId(mealId);
        }
        meal.setMealName(mealName);
        meal.setCalories(calories);
        meal.setFat(fat);
        meal.setCarbohydrates(carbohydrates);
        meal.setProtein(protein);
        meal.setDate(date);
        if (node.has("foods")) {
            JsonNode foods_node = node.get("foods");
            Food[] foods = mapper.treeToValue(foods_node, Food[].class);
            for (Food food : foods){
                meal.addFood(food);
            }
        }
		return meal;
	}

}
