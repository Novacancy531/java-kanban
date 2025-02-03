import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    // Не забыть вернуть приват.
     static HashMap<Integer, Object> tasks = new HashMap<>();
      static HashMap<Integer, Object> subtasks = new HashMap<>();
     static HashMap<Integer, Object> epics =new HashMap<>();
    protected static int numberOfTasks = 0;

    public static HashMap<Integer, Object> getSubtasks() {
        return subtasks;
    }

    // Пункт А: Получение списка всех задач.
    static HashMap getList () {
        HashMap<Integer, Object> getList = new HashMap<>();
        getList.putAll(tasks);
        getList.putAll(subtasks);
        getList.putAll(epics);
        return getList;
    }

    // Пункт B: Удаление всех задач.
    static void clearAll() {
        tasks.clear();
        subtasks.clear();
        epics.clear();
        numberOfTasks = 0;
    }

    // Пункт C: Получение по идентификатору.
    static Object getById(int id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        } else if (subtasks.containsKey(id)) {
            return subtasks.get(id);
        } else if (epics.containsKey(id)) {
            return epics.get(id);
        }
        return null;
    }

    // Пункт D: Создание задач/эпиков/подзадач.
    static void addTask(Object object, Epic epic) {
        if (object.getClass() == Task.class) {
            tasks.put(object.hashCode(), object);
        } else if (object.getClass() == Subtask.class) {
            subtasks.put(object.hashCode(), object);
            ((Subtask) object).setEpicID(epic.getId());
            epic.addSubtask((Subtask) object);
            epic.checkEpicStatus();
        } else if (object.getClass() == Epic.class) {
            epics.put(object.hashCode(), object);
        }
    }

    // Пункт F: Удаление по идентификатору.
    static void deleteTask(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else if (subtasks.containsKey(id)) {
            Subtask subtask = (Subtask) subtasks.get(id);
            Epic epic = (Epic) epics.get(subtask.getEpicID());
            epic.subtasksList.remove(Integer.valueOf(id));
            epic.checkEpicStatus();
            subtasks.remove(id);
        } else if (epics.containsKey(id)) {
            deleteEpicSubtasks((Epic) epics.get(id));
            epics.remove(id);
        }

    }

    static void deleteEpicSubtasks(Epic epic) {
        ArrayList<Integer> subtasksDeleteList = epic.getSubtasksList();
            for (int id : subtasksDeleteList) {
                subtasks.remove(id);
            }
    }

    static void updateTask(int id, Object object) {
        if (object.getClass() == Task.class) {
            tasks.replace(id, object);
            ((Task) object).setId(id);
        } else if (object.getClass() == Subtask.class) {
            subtasks.replace(id, object);
            ((Subtask) object).setId(id);
        } /*else if (object.getClass() == Subtask.class) {
            subtasks.put(object.hashCode(), object);
            ((Subtask) object).setEpicID(epic.getId());
            epic.addSubtask((Subtask) object);
            epic.checkEpicStatus();
        }*/
    }




}