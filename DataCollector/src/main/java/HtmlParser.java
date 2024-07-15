import core.Metro;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HtmlParser {
    private Document doc;
    Metro metro;

    public HtmlParser() {}
    public HtmlParser(Metro metro) {
        try {
            this.metro = metro;
            init();
            setLine();
            setStations();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void init() throws IOException{
        String urlSpbMetro = "https://skillbox-java.github.io";
        this.doc = Jsoup.connect(urlSpbMetro).get();
    }

    private void setLine() {
        Elements lines = doc.select(".js-metro-line");
        for (Element line : lines) {
            String nameLine = line.text();
            String numberLine = line.attr("data-line");

            metro.addLine(nameLine, numberLine);
        }
    }

    private void setStations() {
        Elements lines = doc.select(".js-metro-stations");
        for (Element line : lines) {
            Elements stations = line.select(".single-station");
            for (Element station : stations) {
                String nameStation = station.select(".name").text();
                String numberLine = line.attr("data-line");
                boolean isConnections = station.select("span").hasClass("t-icon-metroln");
                metro.addStations(nameStation, numberLine, isConnections);
            }
        }
    }
}
