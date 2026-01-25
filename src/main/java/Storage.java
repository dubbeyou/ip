import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final File file;

    public Storage(String filepath) {
        this.file = new File(filepath);
        createFile();
    }

    private void createFile() {
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create storage file.", e);
        }
    }

    public ArrayList<Task> loadTasks() throws SnekException{
        ArrayList<Task> taskList = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Task task = Parser.parseTaskFromFile(line);
                taskList.add(task);
            }
        } catch (FileNotFoundException ignore) {
            return new ArrayList<>();
        }
        return taskList;
    }

    public void write(Task task) {
        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write(task.getSaveString() + "\n");
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to storage file.", e);
        }
    }

    public void overwrite(ArrayList<Task> taskList) {
        try (FileWriter fw = new FileWriter(file, false)) {
            for (Task task : taskList) {
                fw.write(task.getSaveString() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to overwrite storage file.", e);
        }
    }
}