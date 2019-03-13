package org.springframework.samples.completefitnesstracker.rest;

import java.io.IOException;

import org.springframework.samples.completefitnesstracker.model.Food;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class JacksonCustomFoodSerializer extends StdSerializer<Food> {

	public JacksonCustomFoodSerializer() {
		this(null);
	}

	public JacksonCustomFoodSerializer(Class<Food> t) {
		super(t);
	}


	@Override
	public void serialize(Food food, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		if (food == null)  {
			throw new IOException("Cannot serialize Food object - food or food.pet is null");
        }

		jgen.writeStartObject();
		if (food.getId() == null) {
			jgen.writeNullField("id");
		} else {
			jgen.writeNumberField("id", food.getId());
        }

        jgen.writeNumberField("calories", food.getCalories());
        jgen.writeNumberField("fat", food.getFat());
        jgen.writeNumberField("carbohydrates", food.getCarbohydrates());
        jgen.writeNumberField("protein", food.getProtein());
        jgen.writeStringField("foodName", food.getFoodName());
        jgen.writeNumberField("servings", food.getServings());

		jgen.writeEndObject();
	}

}
