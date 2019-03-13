package org.springframework.samples.completefitnesstracker.rest;

import java.io.IOException;

import org.springframework.samples.completefitnesstracker.model.Athlete;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class JacksonCustomAthleteSerializer extends StdSerializer<Athlete> {

    public JacksonCustomAthleteSerializer() {
        this(null);
    }

    public JacksonCustomAthleteSerializer(Class<Athlete> t) {
        super(t);
    }

    @Override
    public void serialize(Athlete athlete, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (athlete == null) {
            throw new IOException("Cannot serialize Food object - food or food.athlete is null");
        }

        jgen.writeStartObject();
        if (athlete.getId() == null) {
            jgen.writeNullField("id");
        } else {
            jgen.writeNumberField("id", athlete.getId());
        }

        jgen.writeStringField("username", athlete.getUsername());
        jgen.writeStringField("password", athlete.getPassword());
        jgen.writeStringField("email", athlete.getEmail());


        jgen.writeEndObject();
    }

}
