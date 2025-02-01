import java.util.HashMap;

public class TaskManager {



    private static HashMap<Integer, Object> tasksMap = new HashMap<>();
    protected static int taskScore = 0;


    static Object outputAllTasks() {
        return tasksMap.values();
    }

    static void clearAllTasks() { // Метод B
        tasksMap.clear();
        taskScore = 0;
    }

    static Object getById(int id) { // Работает
        return tasksMap.get(id);
    }

    static void addTask(Task task) { // Работает
        tasksMap.put(task.getId(), task);
    }

    static void addEpic(Epic epic) {
        tasksMap.put(epic.getId(), epic);
    }

    static void updateTask(int id, Task task) { // Не работает
        tasksMap.replace(id, task);
    }

    static void removeById(int id) { // Работает
        tasksMap.remove(id);
    }
}