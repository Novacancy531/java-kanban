package assistants;

import tasks.Task;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class PrioritizedTasks {

    private final TreeSet<Task> tasks;


    public PrioritizedTasks() {
        tasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));
    }

    public SortedSet<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        if(task.getStartTime() != null) {
            tasks.add(task);
        }
    }

    public void updateTask(Task oldTask, Task task) {
        if(task.getStartTime() != null) {
            tasks.add(task);
        } else {
            tasks.remove(oldTask);
        }
    }

    public void deleteTask(Task task) {
        tasks.remove(task);
    }

    public void deleteTasks(Map<Integer, ? extends Task> map) {
        for (Task task : map.values()) {
            tasks.remove(task);
        }
    }
}
