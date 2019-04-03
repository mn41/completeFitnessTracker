package org.springframework.samples.completefitnesstracker.rest;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.samples.completefitnesstracker.model.Food;
import org.springframework.samples.completefitnesstracker.model.Meal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

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
        String dateStr = node.get("date").asText(null);
		try {
			date = formatter.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new IOException(e);
		}

		if(node.has("id")) {
            int mealId = node.get("id").asInt();
            if (!(mealId == -1)) {
                meal.setId(mealId);
            }
        }
        if(node.has("mealName")) {
            String mealName = node.get("mealName").asText(null);
            meal.setMealName(mealName);
        }

        if(node.has("fat")) {
            double fat = node.get("fat").asDouble();
            meal.setFat(fat);
        }

        if(node.has("carbohydrates")) {
            double carbohydrates = node.get("carbohydrates").asDouble();
            meal.setCarbohydrates(carbohydrates);
        }

        if(node.has("protein")) {
            double protein = node.get("protein").asDouble();
            meal.setProtein(protein);
        }

        if(node.has("calories")) {
            double calories = node.get("calories").asDouble();
            meal.setCalories(calories);
        }
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
