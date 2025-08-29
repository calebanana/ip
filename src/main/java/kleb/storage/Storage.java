package kleb.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final String saveDirectory;
    private final String saveFileName;

    public Storage(String pathString) {
        int lastSlash = pathString.lastIndexOf("/");
        String dirString = pathString.substring(0, lastSlash);
        String fileString = pathString.substring(lastSlash);
        this.saveDirectory = dirString;
        this.saveFileName = fileString;
    }

    private String getFullPath(){
        return this.saveDirectory + this.saveFileName;
    }

    public List<String> readFile() {
        File save_dir = new File(this.saveDirectory);
        File save_file = new File(this.getFullPath());

        if (!save_dir.exists()) {
            save_dir.mkdirs();
        }
        if (!save_file.exists()) {
            try {
                save_file.createNewFile();
                System.out.println("Created file.");

            } catch (IOException e) {
                System.out.println("Unable to create save file.");
            }
        }

        try {
            return Files.readAllLines(Paths.get(this.getFullPath()));

        } catch (IOException e) {
            System.out.println("Unable to read save file.");
        }
        return new ArrayList<>();
    }

    public void save(List<String> saveList) {
        try (FileWriter fileWriter = new FileWriter(this.getFullPath())) {
            for (String task : saveList) {
                fileWriter.write(task + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error when writing to file.");
        }
    }
}
