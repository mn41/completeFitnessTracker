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

public class JacksonCustomFoodDeserializer extends StdDeserializer<Food> {

	public JacksonCustomFoodDeserializer() {
		this(null);
	}

	public JacksonCustomFoodDeserializer(Class<Food> t) {
		super(t);
	}

	@Override
	public Food deserialize(JsonParser parser, DeserializationContext context)	throws IOException, JsonProcessingException {
        Food food = new Food();
        Meal meal = new Meal();
		ObjectMapper mapper = new ObjectMapper();
        JsonNode node = parser.getCodec().readTree(parser);

        int foodId = node.get("id").asInt();
        String foodName = node.get("foodName").asText(null);
        double servings = node.get("servings").asDouble();
        double calories = node.get("calories").asDouble();
        double fat = node.get("fat").asDouble();
        double carbohydrates = node.get("carbohydrates").asDouble();
		double protein = node.get("protein").asDouble();

		if (!(foodId == -1)) {
			food.setId(foodId);
        }
        food.setFoodName(foodName);
        food.setServings(servings);
        food.setCalories(calories);
        food.setFat(fat);
        food.setCarbohydrates(carbohydrates);
        food.setProtein(protein);
		return food;
	}

}
