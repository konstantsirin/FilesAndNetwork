package core;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;

import org.apache.commons.lang3.StringUtils;

public class Metro {
    private final ArrayList<Line> allLinesMetro;
    private final String pathToOut = "./DataCollector/out/";

    public Metro() {
        this.allLinesMetro = new ArrayList<>();
    }

    public void addLine(String nameLine, String numberLine) {
        Line line = new Line(nameLine, numberLine);
        this.allLinesMetro.add(line);
    }

    public void addStations(String nameStation, String numberLine, boolean isConnections) {
        Station currentStation = new Station(nameStation, numberLine, isConnections);

        allLinesMetro.stream()
                .filter(l -> l.getNumberLine().equals(numberLine))
                .forEach(l -> l.addStation(currentStation));
    }

    public void getLines() {
        allLinesMetro.forEach(System.out::println);
    }

    public void getLine(String numLine) {
        allLinesMetro.stream()
                .filter(l -> l.getNumberLine().equals(numLine))
                .forEach(System.out::println);

    }

    public void getStations() {
        allLinesMetro.stream()
                .flatMap(line -> line.getStations().stream())
                .forEach(System.out::println);
    }

    public void getStations(String numLine) {
        allLinesMetro.stream()
                .filter(line -> line.getNumberLine().equals(numLine))
                .flatMap(line -> line.getStations().stream())
                .forEach(System.out::println);
    }

    public ArrayList<Line> getAllLinesMetro() {
        return allLinesMetro;
    }

    public void writeMapToJson() {
        JSONObject object = new JSONObject();
        JSONObject objectStations = new JSONObject();
        JSONArray lines = new JSONArray();

        for (Line line : allLinesMetro) {
            JSONArray stationsInLine = new JSONArray();

            JSONObject dataLine = new JSONObject();
            dataLine.put("number", line.getNumberLine());
            dataLine.put("name", line.getName());
            lines.add(dataLine);

            for (Station station : line.getStations()) {
                stationsInLine.add(station.getName());
            }
            objectStations.put(line.getNumberLine(), stationsInLine);
        }

        object.put("stations", objectStations);
        object.put("lines", lines);


        String FILENAME_JSON_MAP = pathToOut + "map.json";
        try (FileWriter writer = new FileWriter(FILENAME_JSON_MAP)) {
            writer.write(object.toJSONString());
            writer.flush();
            // writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void writeStationsToJson() {
        JSONObject object = new JSONObject();
        JSONArray objectStations = new JSONArray();


        for (Line line : allLinesMetro) {
            for (Station station : line.getStations()) {
                String nameStation = station.getName();
                String nameLine = line.getName();
                String dateCreateStation = station.getDateCreate();
                double depth = station.getDepth();
                boolean isConnection = station.getIsConnections();

                JSONObject dataStation = new JSONObject();
                if (StringUtils.isNotBlank(nameStation)) {
                    dataStation.put("name", nameStation);
                }
                if (StringUtils.isNotBlank(nameLine)) {
                    dataStation.put("line", nameLine);
                }
                if (StringUtils.isNotBlank(dateCreateStation)) {
                    dataStation.put("date", dateCreateStation);
                }
                if (depth != 0) {
                    dataStation.put("depth", depth);
                }

                dataStation.put("hasConnection", isConnection);

                objectStations.add(dataStation);
            }
        }
        object.put("stations", objectStations);

        String FILENAME_JSON_STATIONS = pathToOut + "stations.json";
        try (FileWriter writer = new FileWriter(FILENAME_JSON_STATIONS)) {
            writer.write(object.toJSONString());
            writer.flush();
            // writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
