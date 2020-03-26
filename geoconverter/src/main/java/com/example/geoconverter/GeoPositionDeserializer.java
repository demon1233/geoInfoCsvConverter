package com.example.geoconverter;

import com.example.geoconverter.dao.GeoPosition;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;


public class GeoPositionDeserializer extends StdDeserializer<GeoPosition> {

    protected GeoPositionDeserializer() {
        super(GeoPosition.class);
    }

    protected GeoPositionDeserializer(Class<?> vc) {
        super(vc);
    }

    protected GeoPositionDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected GeoPositionDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    @Override
    public GeoPosition deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode geoPositiNode = jp.getCodec().readTree(jp);
        GeoPosition geoPosition = new GeoPosition();
        String[] child = geoPositiNode.path("geo_position").textValue().replace("{", "").replace("}", "").split(",");
        String[] value = child[0].split("=");
        String[] valuchid1 = child[1].split("=");
        geoPosition.setLatitude(value[1]);
        geoPosition.setLongitude(valuchid1[1]);
        geoPosition.setId(geoPositiNode.get("_id").textValue());

        return geoPosition;
    }
}