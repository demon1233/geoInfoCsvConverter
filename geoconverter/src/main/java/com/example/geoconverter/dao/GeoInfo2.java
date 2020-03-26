package com.example.geoconverter.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder
public class GeoInfo2 {
    //TODO Remove
    private String position;
    private String key;
    private String name;
    private String fullName;
    @JsonProperty("iata_airport_code")
    private String iataAirportCode;
    @JsonProperty("_type")
    private String type;
    @JsonProperty("_id")
    private String id;
    private String country;
    @JsonProperty("geo_position")
    private String geoPosition;
    @JsonProperty("location_id")
    private Long locationId;
    private Boolean inEurope;
    private String countryCode;
    private Boolean coreCountry;
    private Long distance;

}
