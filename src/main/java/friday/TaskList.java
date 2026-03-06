package friday;

import java.util.ArrayList;

import friday.task.Task;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task getTask(int taskNumber) {
        validateTaskNumber(taskNumber);
        return tasks.get(taskNumber - 1);
    }

    public Task deleteTask(int taskNumber) {
        validateTaskNumber(taskNumber);
        return tasks.remove(taskNumber - 1);
    }

    public void markTask(int taskNumber) {
        validateTaskNumber(taskNumber);
        tasks.get(taskNumber - 1).markAsDone();
    }

    public void unmarkTask(int taskNumber) {
        validateTaskNumber(taskNumber);
        tasks.get(taskNumber - 1).markAsNotDone();
    }

    private void validateTaskNumber(int taskNumber) {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new IndexOutOfBoundsException("Invalid task number");
        }
    }
}
