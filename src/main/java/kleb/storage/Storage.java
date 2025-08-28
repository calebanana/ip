package kleb.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages loading and saving tasks to a file.
 * Handles file and directory creation if they do not exist.
 */
public class Storage {
    private final String saveDirectory;
    private final String saveFileName;

    /**
     * Constructs a new Storage instance from a full file path.
     * It separates the directory and the file name from the path string.
     *
     * @param pathString The full path to the save file.
     */
    public Storage(String pathString) {
        int lastSlash = pathString.lastIndexOf("/");
        String dirString = pathString.substring(0, lastSlash);
        String fileString = pathString.substring(lastSlash);
        this.saveDirectory = dirString;
        this.saveFileName = fileString;
    }

    /**
     * Combines the directory and file name to get the full path.
     * @return The complete file path string.
     */
    private String getFullPath(){
        return this.saveDirectory + this.saveFileName;
    }

    /**
     * Reads all lines from the save file into a list of strings.
     * Creates the directory and file if they don't exist.
     *
     * @return A list of strings representing the saved tasks.
     */
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

    /**
     * Writes a list of task strings to the save file, overwriting existing content.
     *
     * @param saveList The list of strings to save.
     */
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
