import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import core.Line;
import core.Metro;
import core.Station;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

class CsvParser {

    private String path;
    static Metro metro;

    public CsvParser() {
    }

    public CsvParser(String path, Metro metro) {
        setPath(path);
        setMetro(metro);
        try {
            parseStations();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void setPath(String path) {this.path = path;}
    private void setMetro(Metro metro) {
        CsvParser.metro = metro;}
    private void parseStations() throws IOException {
        Reader myReader = new FileReader(path);
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(Element.class)
                .withColumnSeparator(',').withSkipFirstDataRow(true);
        MappingIterator<Element> iterator = mapper
                .readerFor(Element.class)
                .with(schema)
                .readValues(myReader);

        List<Element> elements = iterator.readAll();

        for (Element element : elements) {
            element.addDateCreateStation();
        }

    }

    @JsonPropertyOrder({"name", "date"})
    static class Element {
        public String name;
        public String date;

        public void addDateCreateStation() {
            String nameUtf8 = new String(name.getBytes(Charset.forName("cp1251")),
                    StandardCharsets.UTF_8);
            ArrayList<Line> allLinesMetro = metro.getAllLinesMetro();
            for (Line line : allLinesMetro) {
                ArrayList<Station> stations = line.getStations();
                for (Station station : stations) {

                    if (station.getName().equalsIgnoreCase(nameUtf8) && station.getDateCreate().isEmpty()) {
                        station.setDateCreate(date);
                    }
                }
            }
        }

        @Override
        public String toString() {
            return "Element{" + "name=" + name + ", date=" + date + "}";
        }
    }
}


