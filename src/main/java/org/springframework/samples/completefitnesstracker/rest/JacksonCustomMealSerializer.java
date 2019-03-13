package org.springframework.samples.completefitnesstracker.rest;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;

import org.springframework.samples.completefitnesstracker.model.Food;
import org.springframework.samples.completefitnesstracker.model.Meal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

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
