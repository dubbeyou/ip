import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final File file;

    public Storage(String filepath) throws StorageSnekException {
        this.file = new File(filepath);
        createFile();
    }

    private void createFile() throws StorageSnekException {
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageSnekException(Messages.MESSAGE_ERROR_LOAD);
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

    public void write(Task task) throws StorageSnekException{
        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write(task.getSaveString() + "\n");
            fw.close();
        } catch (IOException e) {
            throw new StorageSnekException(Messages.MESSAGE_ERROR_WRITE);
        }
    }

    public void overwrite(ArrayList<Task> taskList) throws StorageSnekException {
        try (FileWriter fw = new FileWriter(file, false)) {
            for (Task task : taskList) {
                fw.write(task.getSaveString() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            throw new StorageSnekException(Messages.MESSAGE_ERROR_WRITE);
        }
    }
}