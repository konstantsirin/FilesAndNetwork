import core.Metro;

public class Main {

    public static void main(String[] args) {
        System.out.println("Program for parsing files");

        Metro mskMetro = new Metro();
        HtmlParser htmlParser = new HtmlParser(mskMetro);

        FinderFilesInFolders finderFiles = new FinderFilesInFolders(mskMetro);
        finderFiles.browsingFolder("./DataCollector/data");
        mskMetro.writeMapToJson();
        mskMetro.writeStationsToJson();
    }

}