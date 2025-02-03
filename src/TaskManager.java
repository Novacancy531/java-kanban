import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    TaskManager(){}

    static HashMap<Integer, Object> tasks = new HashMap<>();
    static HashMap<Integer, Object> subtasks = new HashMap<>();
    static HashMap<Integer, Object> epics = new HashMap<>();
    protected static int numberOfTasks = 0;

    static HashMap<Integer, Object> getAllTasks() { // Пункт А: Получение списка задач.
        HashMap<Integer, Object> allTasks = new HashMap<>();
        allTasks.putAll(tasks);
        allTasks.putAll(subtasks);
        allTasks.putAll(epics);
        return allTasks;
    }

    static void removeAllTasks() { // Пункт B: Удаление всех задач.
        tasks.clear();
        subtasks.clear();
        epics.clear();
    }

    static Object getTaskById(int id) { // Пункт C: Получение по идентификатору.
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        } else if (subtasks.containsKey(id)) {
            return subtasks.get(id);
        } else if (epics.containsKey(id)) {
            return epics.get(id);
        }
        return null;
    }

    static void addTask(Task task) {  // Пункт D: Создание 1/3.
        tasks.put(task.hashCode(), task);
    }

    static void addTask(Subtask subtask, Epic epic) { // Пункт D: Создание 2/3.
        subtasks.put(subtask.hashCode(), subtask);
        subtask.setEpicId(epic.hashCode());
        epic.getSubtasksList().add(subtask.hashCode());
        updateStatus(epic);
    }

    static void addTask(Epic epic) { // Пункт D: Создание 3/3.
        epics.put(epic.hashCode(), epic);
        updateStatus(epic);
    }

    static void updateTask(Task task, int id) { // Пункт E: Обновление 1/3.
        tasks.replace(id, task);
        task.setId(id);
    }

    static void updateTask(Subtask subtask, int id) { // Пункт E: Обновление 2/3.
        Subtask temporarySubtask = (Subtask) subtasks.get(id);
        Epic temporaryEpic = (Epic) epics.get(temporarySubtask.getEpicId());
        subtasks.replace(id, subtask);
        subtask.setId(id);
        updateStatus(temporaryEpic);
    }

    static void updateTask(Epic epic, int id) { // Пункт E: Обновление 3/3.
        Epic temporaryEpic = (Epic) epics.get(id);
        epic.setSubtasksList(temporaryEpic.getSubtasksList());
        epics.replace(id, epic);
        epic.setId(id);
        updateStatus(epic);
    }

    static void deleteTaskById(int id) { // Пункт F: Удаление по идентификатору.
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else if (subtasks.containsKey(id)) {
            Subtask subtask = (Subtask) subtasks.get(id);
            Epic epic = (Epic) epics.get(subtask.getEpicId());
            epic.getSubtasksList().remove(epic.getSubtasksList().indexOf(id));
            subtasks.remove(id);
            updateStatus(epic);
        } else if (epics.containsKey(id)) {
            Epic epic = (Epic) epics.get(id);
            ArrayList<Integer> subtasks = epic.getSubtasksList();
            for (int taskId : subtasks) {
                subtasks.remove(subtasks.indexOf(taskId));
            }
            epics.remove(id);
        }
    }

    static void updateStatus(Epic epic) {
        if (epic.getSubtasksList().isEmpty()) {
            epic.setStatus(Status.NEW);
        } else {
            epic.setStatus(epic.checkEpicStatus());
        }
    }

    static HashMap<Integer, Object> getTaskFromEpic(Epic epic){ // Пункт 3A: Получение списка подзадач эпика.
        HashMap<Integer, Object> tasksFromEpic = new HashMap<>();
        for (int id: epic.getSubtasksList()) {
            tasksFromEpic.put(id, subtasks.get(id));
        }
        return tasksFromEpic;
    }
}


