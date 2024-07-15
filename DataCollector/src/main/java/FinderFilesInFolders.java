import core.Metro;

import java.nio.file.*;
import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributes;

public class FinderFilesInFolders {
    private Metro metro;

    public FinderFilesInFolders() {
    }

    public FinderFilesInFolders(Metro metro) {
        setMetro(metro);
    }

    public void setMetro(Metro metro) {
        this.metro = metro;
    }

    public void browsingFolder(String directoryPath) {

        Path path = Paths.get(directoryPath);
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    String fileName = file.toAbsolutePath().toString();
                    // System.out.println("Τΰιλ: " + fileName);

                    if (fileName.contains(".")) {
                        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
                        if (fileExtension.equals("csv")) {
                            System.out.println("Τΰιλ CSV: " + fileName);
                            CsvParser csvParser = new CsvParser(fileName, metro);
                        }

                        if (fileExtension.equals("json")) {
                            System.out.println("Τΰιλ JSON: " + fileName);
                            JsonParser jsonParser = new JsonParser(fileName, metro);
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
