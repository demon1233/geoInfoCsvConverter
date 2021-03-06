package com.example.geoconverter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.io.IOException;

@SpringBootTest
class CsvServiceTest {


    @Autowired
    private CsvService csvService;

    @Test
    public void shouldConvertJsonToCsv() throws IOException {
        String json = "[{\"position\":\"Vancover\",\"key\":\"key\",\"name\":\"samplename\",\"fullName\":\"SampleName\",\"country\":null,\"inEurope\":true,\"countryCode\":\"US\",\"coreCountry\":true,\"distance\":40,\"iata_airport_code\":\"432\",\"_type\":\"type10\",\"_id\":\"1230\",\"geo_position\":\"{latitude=111.123, longitude=2222.2222}\",\"location_id\":13}]";


        String expected = "position,key,name,fullName,country,inEurope,countryCode,coreCountry,distance,iata_airport_code,_type,_id,geo_position,location_id\n" +
                "Vancover,key,samplename,SampleName,,true,US,true,40,432,type10,1230,\"{latitude=111.123, longitude=2222.2222}\",13\n";
        Assert.isTrue(csvService.readJsonAsCsv(json).equals(expected));
    }


    @Test
    public void shouldConvertJsonToGeoPositionCSV() throws IOException {

        String json = "[{\"position\":\"Vancover\",\"key\":\"key\",\"name\":\"samplename\",\"fullName\":\"SampleName\",\"country\":null,\"inEurope\":true,\"countryCode\":\"US\",\"coreCountry\":true,\"distance\":40,\"iata_airport_code\":\"432\",\"_type\":\"type10\",\"_id\":\"1230\",\"geo_position\":\"{latitude=111.123, longitude=2222.2222}\",\"location_id\":13}]";

        String expectedResult = "'id, latitude, longitude'\n" +
                "'1230,111.123,2222.2222'\n";

        Assert.isTrue(csvService.getGeoPositionsAsCsv(csvService.getGeoPositions(json)).equals(expectedResult));
    }


}