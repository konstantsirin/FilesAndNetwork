package core;

import java.util.ArrayList;

public class Line {
    private String name;
    private String numberLine;
    private final ArrayList<Station> stations;
    public Line(String name, String numberLine) {
        setName(name);
        setNumberLine(numberLine);
        this.stations = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getNumberLine() {
        return numberLine;
    }

    private void setNumberLine(String numberLine) {
        this.numberLine = numberLine;
    }

    public void addStation(Station station) {
        stations.add(station);
    }

    public ArrayList<Station> getStations() {
        return stations;
    }

    public String toString() {
        return "Номер линии: " + getNumberLine() + "; Название линии: " + getName() + ";";
    }
}
