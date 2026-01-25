import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.management.openmbean.ArrayType;

public class Storage {
    private static final String STORAGEPATH = "./data";
    private static final String FILENAME = "snek.txt";  

    private final File file;

    public Storage() {
        this.file = new File(STORAGEPATH, FILENAME);
    }

    private void createStorageFile() {
        File dir = new File(STORAGEPATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create storage file.", e);
        }
    }

    public ArrayList<Task> loadTasks() {
        ArrayList<Task> taskList = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Task task = parser.parseTaskFromFile(line); // TO BE IMPLEMENTED
                taskList.add(task);
            }
        } catch (FileNotFoundException ignore) {
            return new ArrayList<>()
        }
        return taskList;
    }
}