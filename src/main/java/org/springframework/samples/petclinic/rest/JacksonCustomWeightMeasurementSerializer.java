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

import org.springframework.samples.petclinic.model.WeightMeasurement;
import org.springframework.samples.petclinic.model.Meal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * @author Vitaliy Fedoriv
 *
 */

public class JacksonCustomWeightMeasurementSerializer extends StdSerializer<WeightMeasurement> {

	public JacksonCustomWeightMeasurementSerializer() {
		this(null);
	}

	public JacksonCustomWeightMeasurementSerializer(Class<WeightMeasurement> t) {
		super(t);
	}


	@Override
	public void serialize(WeightMeasurement weightMeasurement, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		if (weightMeasurement == null)  {
			throw new IOException("Cannot serialize WeightMeasurement object - weightMeasurement or weightMeasurement.pet is null");
        }
        Format formatter = new SimpleDateFormat("yyyy/MM/dd");
		jgen.writeStartObject();
		if (weightMeasurement.getId() == null) {
			jgen.writeNullField("id");
		} else {
			jgen.writeNumberField("id", weightMeasurement.getId());
        }

        jgen.writeNumberField("weight", weightMeasurement.getWeight());
        jgen.writeStringField("date", formatter.format(weightMeasurement.getDate()));

		jgen.writeEndObject();
	}

}
