package org.springframework.samples.completefitnesstracker.rest;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;

import org.springframework.samples.completefitnesstracker.model.WeightMeasurement;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

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
