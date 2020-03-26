package com.example.geoconverter.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class GeoInfo {

    @JsonIgnore
    private String position;
    @JsonIgnore
    private String key;
    @JsonIgnore
    private String name;
    @JsonIgnore
    private String fullName;


    @JsonProperty("iata_airport_code")
    @JsonIgnore
    private String iataAirportCode;
    @JsonProperty("_type")
    private String type;
    @JsonProperty("_id")
    private String id;
    @JsonIgnore
    private String country;
    @JsonProperty("geo_position")
    private String geoPosition;
    @JsonProperty("location_id")
    private String locationId;
    @JsonIgnore
    private Boolean inEurope;
    @JsonIgnore
    private String countryCode;
    @JsonIgnore
    private Boolean coreCountry;
    @JsonIgnore
    private String distance;

}
