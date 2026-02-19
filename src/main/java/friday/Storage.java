package friday;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import friday.task.Deadline;
import friday.task.Event;
import friday.task.Task;
import friday.task.Todo;

public class Storage {

    private static final Path FILE_PATH = Paths.get("data", "friday.txt");

    public static void saveTasks(Task[] tasks, int taskCount) {
        try {
            Files.createDirectories(FILE_PATH.getParent());
            FileWriter writer = new FileWriter(FILE_PATH.toFile());
            for (int i = 0; i < taskCount; i++) {
                writer.write(taskToFileString(tasks[i]) + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    public static int loadTasks(Task[] tasks) {
        File file = FILE_PATH.toFile();
        if (!file.exists()) {
            return 0;
        }

        int count = 0;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                Task task = parseTask(line);
                if (task != null) {
                    tasks[count] = task;
                    count++;
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return count;
    }

    private static String taskToFileString(Task task) {
        String doneStr = task.isDone() ? "1" : "0";
        if (task instanceof Todo) {
            return "T | " + doneStr + " | " + task.getDescription();
        } else if (task instanceof Deadline) {
            Deadline d = (Deadline) task;
            return "D | " + doneStr + " | " + d.getDescription() + " | " + d.getBy();
        } else if (task instanceof Event) {
            Event e = (Event) task;
            return "E | " + doneStr + " | " + e.getDescription() + " | " + e.getFrom() + " | " + e.getTo();
        }
        return "";
    }

    private static Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            return null;
        }

        String type = parts[0].trim();
        boolean isDone = parts[1].trim().equals("1");
        String description = parts[2].trim();

        Task task = null;
        switch (type) {
        case "T":
            task = new Todo(description);
            break;
        case "D":
            if (parts.length >= 4) {
                task = new Deadline(description, parts[3].trim());
            }
            break;
        case "E":
            if (parts.length >= 5) {
                task = new Event(description, parts[3].trim(), parts[4].trim());
            }
            break;
        default:
            return null;
        }

        if (task != null && isDone) {
            task.markAsDone();
        }
        return task;
    }
}
