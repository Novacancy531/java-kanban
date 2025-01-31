import java.util.HashMap;
import java.util.ArrayList;

public class TaskManager {

    private static HashMap<Integer, Object> tasksMap = new HashMap<>();
    protected static int taskScore = 1;


    static ArrayList outputAllTasks() {
        ArrayList<Object> allTasks = new ArrayList<>();
        for (Object task : tasksMap.values()) {
            allTasks.add(task);
        }
        return allTasks;
    }

    static void clearAllTasks() {
        tasksMap.clear();
    }

    static Object getById(int id) {
        return tasksMap.get(id);
    }

    static void addTask(Task task) {
        tasksMap.put(task.getId(), task);
    }

    static void updateTask(Task task) {
        tasksMap.replace(task.getId(), task);
    }

    static void removeById(int id) {
        tasksMap.remove(id);
    }

    // тест
    static void printAll() {
        for (Object task : tasksMap.keySet()) {
            System.out.println("id: " + task);
            System.out.println(tasksMap.get(task));
        }
    }
    // тест


}