package com.example.geoconverter;


import com.example.geoconverter.dao.GeoInfo;
import com.example.geoconverter.dao.GeoPosition;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/getting/json")
public class CsvReaderController {

    @Autowired
    private RestClient restClient;

    @Autowired
    private CsvService csvService;


    @GetMapping(value = "/{size}", produces = "application/json")
    @ResponseBody
    public String getGeoLocationList(@PathVariable Integer size) throws Exception {


        String json = restClient.getClient().getForObject("http://localhost:8084/generate/json/" + size, String.class);
        saveToCsvFile(json, size);
        return json;
    }


    private void saveToCsvFile(String json, int size) throws Exception {
        csvService.convertJsonStringToCsv(json, size);
    }


    @GetMapping(value = "{size}/getItem", produces = "application/json")
    @ResponseBody
    public GeoPosition getGeolocationWithParams(@PathVariable Integer size, @RequestParam("id") int id, @RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude) throws IOException {
        File output = new File("src/main/resources/sample-" + size + ".json");

        ObjectMapper mapper = new ObjectMapper();
        List<GeoPosition> geoPositions = mapper.readValue(output, new TypeReference<List<GeoPosition>>() {
        });


        return geoPositions.stream().filter(item -> Integer.parseInt(item.getId()) == id && Double.parseDouble(item.getLatitude()) == latitude && Double.parseDouble(item.getLongitude()) == longitude).findFirst().get();


    }


    @GetMapping(value = "{size}/geoInfoList", produces = "application/json")
    @ResponseBody
    public List<GeoInfo> getGetGeoInfoListFromCSV(@PathVariable Integer size) throws IOException {


        File input = new File("src/main/resources/sample-" + size + ".csv");

        CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
        CsvMapper csvMapper = new CsvMapper();


        List<Object> readAll = csvMapper.readerFor(GeoInfo.class).with(csvSchema).readValues(input).readAll();

        List<GeoInfo> geoInfoList = new ArrayList<>();

        for (Object obj : readAll) {
            geoInfoList.add((GeoInfo) obj);
        }

        return geoInfoList;
    }
}
