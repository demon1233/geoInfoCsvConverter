package com.example.geoconverter;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@RunWith(SpringRunner.class)
@WebMvcTest(GeoLocationController.class)
public class CsvReaderControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GeoLocationController geoLocationController;


    @Test
    public void shouldReturnResponseFromGeneratorServiceWithOk() throws Exception {
        String json = "[\n" +
                "   {\n" +
                "      \"position\":\"Vancover\",\n" +
                "      \"key\":\"key\",\n" +
                "      \"name\":\"samplename\",\n" +
                "      \"fullName\":\"SampleName\",\n" +
                "      \"country\":null,\n" +
                "      \"inEurope\":true,\n" +
                "      \"countryCode\":\"US\",\n" +
                "      \"coreCountry\":true,\n" +
                "      \"distance\":40,\n" +
                "      \"iata_airport_code\":\"432\",\n" +
                "      \"_type\":\"type10\",\n" +
                "      \"_id\":\"1230\",\n" +
                "      \"geo_position\":\"{latitude=111.123, longitude=2222.2222}\",\n" +
                "      \"location_id\":13\n" +
                "   }\n" +
                "]";

        when(geoLocationController.getGeoLocationList(1)).thenReturn(json);
        mockMvc.perform(get("/getting/json/v1/1").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
    }


    @Test
    public void shouldReturnGeoInfoObjectAsCsvResponseWithStatusOk() throws Exception {

        String csv = "position,key,name,fullName,country,inEurope,countryCode,coreCountry,distance,iata_airport_code,_type,_id,geo_position,location_id\n" +
                "Vancover,key,samplename,SampleName,,true,US,true,40,432,type10,1230,\"{latitude=111.123, longitude=2222.2222}\",13\n";
        when(geoLocationController.getGeoLocationListAsCsv()).thenReturn(csv);
        mockMvc.perform(get("/getting/json/v1/geoinfoCsv").contentType(MediaType.TEXT_PLAIN).content(csv)).andExpect(status().isOk());
    }


    @Test
    public void shouldReturnGePositionObjectAsCsvResponseWithStatusOk() throws Exception {

        String csv= "'id, latitude, longitude'\n" +
                "'1230,111.123,2222.2222'\n";
        when(geoLocationController.getGeoLocationListAsCsv()).thenReturn(csv);
        mockMvc.perform(get("/getting/json/v1/geoPositionCsv").contentType(MediaType.TEXT_PLAIN).content(csv)).andExpect(status().isOk());
    }
}