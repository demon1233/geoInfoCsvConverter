package com.example.geoconverter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/getting/json/v1")
public class GeoLocationController {


    @Autowired
    private RestClient restClient;

    @Autowired
    private CsvService csvService;


    private String json;


    @GetMapping(value = "/{size}", produces = "application/json")
    @ResponseBody
    public String getGeoLocationList(@PathVariable Integer size) {
        json = restClient.getClient().getForObject("http://localhost:8084/generate/json/" + size, String.class);
        return json;
    }


    @GetMapping(value = "/geoinfoCsv")
    @ResponseBody
    public String getGeoLocationListAsCsv() throws Exception {
        return csvService.readJsonAsCsv(json);
    }


    @GetMapping(value = "/geoPositionCsv")
    @ResponseBody
    public String getGeoLocationWithParameters() throws Exception {
        return csvService.getGeoPositionsAsCsv(csvService.getGeoPositions(json));
    }
}
