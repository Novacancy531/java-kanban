package assistants;

import tasks.Task;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class PrioritizedTasks {

    /**
     * Множество для хранения задач в порядке приоритета.
     */
    private final TreeSet<Task> tasks;

    /**
     * Конструктор по умолчанию.
     */
    public PrioritizedTasks() {
        tasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));
    }

    /**
     * Метод получения списка задач по приоритету.
     *
     * @return Множество задач.
     */
    public SortedSet<Task> getTasks() {
        return tasks;
    }

    /**
     * Добавление задачи в множество задач.
     *
     * @param task задача.
     */
    public void addTask(final Task task) {
        if (task.getStartTime() != null) {
            tasks.add(task);
        }
    }

    /**
     * Обновление задачи в множестве задач.
     *
     * @param oldTask старая задача.
     * @param task    новая задача.
     */
    public void updateTask(final Task oldTask, final Task task) {
        if (task.getStartTime() != null) {
            tasks.remove(oldTask);
            tasks.add(task);
        } else {
            tasks.remove(oldTask);
        }
    }

    /**
     * Удаление задачи из множества задач.
     *
     * @param task задача.
     */
    public void deleteTask(final Task task) {
        tasks.remove(task);
    }

    /**
     * Удаление задач по карте задач.
     *
     * @param map карта задач с задачами которые нужно удалить.
     */
    public void deleteTasks(final Map<Integer, ? extends Task> map) {
        for (Task task : map.values()) {
            tasks.remove(task);
        }
    }
}
