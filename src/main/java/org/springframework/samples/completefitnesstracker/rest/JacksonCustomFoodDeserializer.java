package org.springframework.samples.completefitnesstracker.rest;

import java.io.IOException;

import org.springframework.samples.completefitnesstracker.model.Food;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

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
        JsonNode node = parser.getCodec().readTree(parser);

        int foodId = node.get("id").asInt();
        if (!(foodId == -1)) {
			food.setId(foodId);
        }
        if(node.has("foodName")){
        String foodName = node.get("foodName").asText(null);
        food.setFoodName(foodName);
        }

        if(node.has("servings")){
        double servings = node.get("servings").asDouble();
        food.setServings(servings);
        }

        if(node.has("calories")){
        double calories = node.get("calories").asDouble();
        food.setCalories(calories);
        }

        if(node.has("fat")){
        double fat = node.get("fat").asDouble();
        food.setFat(fat);
        }

        if(node.has("carbohydrates")){
        double carbohydrates = node.get("carbohydrates").asDouble();
        food.setCarbohydrates(carbohydrates);
        }

        if(node.has("protein")){
        double protein = node.get("protein").asDouble();
        food.setProtein(protein);
        }

		return food;
	}

}
