package friday;

import java.util.ArrayList;

import friday.task.Task;

/**
 * Wraps task collection operations for Friday.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list from an existing collection.
     *
     * @param tasks tasks to wrap
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns all tasks.
     *
     * @return backing task collection
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Returns task count.
     *
     * @return number of tasks
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns whether the task list is empty.
     *
     * @return true if empty
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Adds a task to the list.
     *
     * @param task task to add
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Gets a task by 1-based task number.
     *
     * @param taskNumber task number
     * @return matching task
     */
    public Task getTask(int taskNumber) {
        validateTaskNumber(taskNumber);
        return tasks.get(taskNumber - 1);
    }

    /**
     * Deletes a task by 1-based task number.
     *
     * @param taskNumber task number
     * @return removed task
     */
    public Task deleteTask(int taskNumber) {
        validateTaskNumber(taskNumber);
        return tasks.remove(taskNumber - 1);
    }

    /**
     * Marks a task as done by task number.
     *
     * @param taskNumber task number
     */
    public void markTask(int taskNumber) {
        validateTaskNumber(taskNumber);
        tasks.get(taskNumber - 1).markAsDone();
    }

    /**
     * Marks a task as not done by task number.
     *
     * @param taskNumber task number
     */
    public void unmarkTask(int taskNumber) {
        validateTaskNumber(taskNumber);
        tasks.get(taskNumber - 1).markAsNotDone();
    }

    /**
     * Finds tasks whose descriptions contain the keyword.
     *
     * @param keyword keyword to search
     * @return matching tasks
     */
    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }

    private void validateTaskNumber(int taskNumber) {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new IndexOutOfBoundsException("Invalid task number");
        }
    }
}
