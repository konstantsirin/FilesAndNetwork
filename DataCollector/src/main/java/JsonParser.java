import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import core.Line;
import core.Metro;
import core.Station;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonParser {
    private Metro metro;

    public JsonParser() {
    }

    public JsonParser(String path, Metro metro) {
        this.metro = metro;

        getDataFromJsonFile(path);

        try {
            JSONParser parser = new JSONParser();
            JSONArray jsonData = (JSONArray) parser.parse(getDataFromJsonFile(path));

            parseStations(jsonData);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String getDataFromJsonFile(String path) {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            lines.forEach(builder::append);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return builder.toString();
    }

    private void parseStations(JSONArray stationsArray) {
        stationsArray.forEach(station ->
        {
            JSONObject stationObject = (JSONObject) station;
            String stationName = (String) stationObject.get("station_name");
            String depth = (String) stationObject.get("depth");
            depth = depth.replace(',', '.');
            ArrayList<Line> allLinesMetro = metro.getAllLinesMetro();
            for (Line line : allLinesMetro) {
                ArrayList<Station> stations = line.getStations();
                for (Station s : stations) {

                    if (s.getName().equalsIgnoreCase(stationName) && s.getDepth() == 0) {
                        try {
                            double depthDouble = Double.parseDouble(depth);
                            if (depthDouble < s.getDepth()) {
                                s.setDepth(depthDouble);
                            }

                            // System.out.println(s);
                        } catch (NumberFormatException ex) {
                            s.setDepth(0.0);
                        }

                    }

                }
            }
        });
    }
}
