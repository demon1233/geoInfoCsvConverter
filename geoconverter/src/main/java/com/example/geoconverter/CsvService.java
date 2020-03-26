package com.example.geoconverter;

import com.example.geoconverter.dao.GeoInfo;
import com.example.geoconverter.dao.GeoPosition;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.github.opendevl.JFlat;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvService {


    public void convertJsonStringToCsv(String json, int size) throws Exception {
        JFlat flatMe = new JFlat(json);

        String filename = "sample-" + size + ".csv";

        flatMe.json2Sheet().headerSeparator(" ").write2csv("src/main/resources/" + filename);
        convertCsvToJsonFile(size, filename);
    }


    private void convertCsvToJsonFile(int size, String filename) throws IOException {

        String filenameJson = "sample-" + size + ".json";

        File input = new File("src/main/resources/" + filename);
        File output = new File("src/main/resources/" + filenameJson);


        CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
        CsvMapper csvMapper = new CsvMapper();

        // Read data from CSV file
        List<Object> readAll = csvMapper.readerFor(GeoInfo.class).with(csvSchema).readValues(input).readAll();


        List<GeoInfo> geoInfoList = new ArrayList<>();

        for (Object obj : readAll) {
            geoInfoList.add((GeoInfo) obj);
        }
        ObjectMapper mapper = new ObjectMapper();

        // Write JSON formated data to output.json file
        mapper.writerWithDefaultPrettyPrinter().writeValue(output, geoInfoList);
    }


    public String readJsonAsCsv(String json) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonTree = mapper.readTree(json);

        CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
        JsonNode firstObject = jsonTree.elements().next();
        firstObject.fieldNames().forEachRemaining(fieldName -> {
            csvSchemaBuilder.addColumn(fieldName);
        });
        CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();


        CsvMapper csvMapper = new CsvMapper();
        return csvMapper.writerFor(JsonNode.class)
                .with(csvSchema).writeValueAsString(jsonTree);


    }


    public List<GeoPosition> getGeoPositions(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, new TypeReference<List<GeoPosition>>() {
        });
    }

    public String getGeoPositionsAsCsv(List<GeoPosition> geoPositions) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("'id, latitude, longitude'").append('\n');
        for (GeoPosition geoPosition : geoPositions
        ) {
            stringBuilder.append("'").append(geoPosition.getId()).append(',').append(geoPosition.getLatitude()).append(",").append(geoPosition.getLongitude()).append("'").append("\n");

        }

        return stringBuilder.toString();
    }
}



