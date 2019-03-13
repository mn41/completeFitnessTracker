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

import org.springframework.samples.petclinic.model.Meal;
import org.springframework.samples.petclinic.model.Food;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * @author Vitaliy Fedoriv
 *
 */

public class JacksonCustomMealSerializer extends StdSerializer<Meal> {

	public JacksonCustomMealSerializer() {
		this(null);
	}

	public JacksonCustomMealSerializer(Class<Meal> t) {
		super(t);
	}


	@Override
	public void serialize(Meal meal, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		if (meal == null)  {
			throw new IOException("Cannot serialize Food object - food or food.meal is null");
        }
        Format formatter = new SimpleDateFormat("yyyy/MM/dd");
		jgen.writeStartObject();
		if (meal.getId() == null) {
			jgen.writeNullField("id");
		} else {
			jgen.writeNumberField("id", meal.getId());
        }
        jgen.writeStringField("date", formatter.format(meal.getDate()));
        jgen.writeNumberField("calories", meal.getCalories());
        jgen.writeNumberField("fat", meal.getFat());
        jgen.writeNumberField("carbohydrates", meal.getCarbohydrates());
        jgen.writeNumberField("protein", meal.getProtein());
        jgen.writeStringField("mealName", meal.getMealName());


        jgen.writeArrayFieldStart("foods");
		for (Food food : meal.getFoods()) {
            jgen.writeStartObject(); // food
            jgen.writeNumberField("id", food.getId());
            jgen.writeNumberField("servings", food.getServings());
			jgen.writeNumberField("calories", food.getCalories());
            jgen.writeNumberField("fat", food.getFat());
            jgen.writeNumberField("carbohydrates", food.getCarbohydrates());
            jgen.writeNumberField("protein", food.getProtein());
            jgen.writeStringField("foodName", food.getFoodName());
			jgen.writeEndObject(); // food
		}
        jgen.writeEndArray();
        jgen.writeEndObject();
	}

}
