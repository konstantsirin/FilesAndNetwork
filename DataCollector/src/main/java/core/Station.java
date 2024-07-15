package core;

public class Station {

    private String name;
    private String numberLine;
    private String dateCreate = "";
    private double depth;
    private boolean isConnections;

    public Station(String name, String numberLine, boolean isConnections) {
        setName(name);
        setNumberLine(numberLine);
        setIsConnections(isConnections);
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

    private void setIsConnections(boolean isConnections) {
        this.isConnections = isConnections;
    }

    public boolean getIsConnections() {
        return isConnections;
    }

    public void setDateCreate(String date) {
        this.dateCreate = date;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    public double getDepth() {
        return depth;
    }

    public String toString() {
        return "Номер линии: " + getNumberLine() + "; Название станции: " + getName() + "; Дата создания станции: "
                + getDateCreate() + "; Глубина станции: " + getDepth() + "; Есть ли на станции переход: "
                + (getIsConnections() ? "Да" : "Нет") + ";";
    }
}
