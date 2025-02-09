import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private int numberOfTasks = 0;

    // Пункт А: Получение списка всех задач.

    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public List<Task> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public List<Task> getEpics() {
        return new ArrayList<>(epics.values());
    }

    // Пункт B: Удаление всех задач.

    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeAllSubtasks() {
        for (Subtask subtask : subtasks.values()) {
            Epic currentEpic = epics.get(subtask.getEpicId());
            List<Integer> temporaryList = currentEpic.getSubtasksList();
            temporaryList.remove(subtask.getId());
            updateEpicStatus(currentEpic);
        }
        subtasks.clear();
    }

    public void removeAllEpics() {
        subtasks.clear();
        epics.clear();
    }

    // Пункт C: Получение по идентификатору.

    public Task receivingTaskById(int id) {
        return tasks.get(id);
    }


    public Subtask receivingSubtaskById(int id) {
        return subtasks.get(id);
    }

    public Epic receivingEpicById(int id) {
        return epics.get(id);
    }

    // Пункт D: Создание.

    public void addTask(Task task) {
        numberOfTasks++;
        task.setId(numberOfTasks);
        tasks.put(task.getId(), task);
    }

    public void addSubtask(Subtask subtask) {
        numberOfTasks++;
        subtask.setId(numberOfTasks);
        subtasks.put(subtask.getId(), subtask);
        Epic temporaryEpic = epics.get(subtask.getEpicId());
        temporaryEpic.getSubtasksList().add(subtask.getId());
        updateEpicStatus(temporaryEpic);
    }

    public void addEpic(Epic epic) {
        numberOfTasks++;
        epic.setId(numberOfTasks);
        epics.put(epic.getId(), epic);
        updateEpicStatus(epic);
    }

    // Пункт E: Обновление.

    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.replace(task.getId(), task);
        }
    }

    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            Subtask temporarySubtask = subtasks.get(subtask.getId());
            Epic temporaryEpic = epics.get(temporarySubtask.getEpicId());
            subtasks.replace(subtask.getId(), subtask);
            updateEpicStatus(temporaryEpic);

        }
    }

    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            Epic temporaryEpic = epics.get(epic.getId());
            epic.setSubtasksList(temporaryEpic.getSubtasksList());
            epics.replace(epic.getId(), epic);
            updateEpicStatus(epic);
        }
    }

    // Пункт F: Удаление по идентификатору.

    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    public void deleteSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        Epic epic = epics.get(subtask.getEpicId());
        epic.getSubtasksList().remove(epic.getSubtasksList().indexOf(id));
        subtasks.remove(id);
        updateEpicStatus(epic);
    }


    public void deleteEpicById(int id) {
        Epic epic = epics.get(id);
        List<Integer> epicSubtasks = epic.getSubtasksList();
        for (int taskId : epicSubtasks) {
            subtasks.remove(taskId);
        }
        epics.remove(id);
    }


    // Пункт 3A: Получение списка подзадач эпика.

    public List<Subtask> getSubtasksFromEpic(Epic epic) {
        ArrayList<Subtask> temporaryOutputList = new ArrayList<>();
        for (int subtaskId : epic.getSubtasksList()) {
            temporaryOutputList.add(subtasks.get(subtaskId));
        }
        return temporaryOutputList;
    }

    public void updateEpicStatus(Epic epic) {
        int resultDone = 0;
        int resultNew = 0;
        List<Integer> temporaryList = epic.getSubtasksList();
        for (int id : temporaryList) {
            Subtask subtask = subtasks.get(id);
            if (subtask.getStatus() == Status.IN_PROGRESS) {
                epic.setStatus(Status.IN_PROGRESS);
                return;
            } else if (subtask.getStatus() == Status.NEW) {
                resultNew++;
            } else if (subtask.getStatus() == Status.DONE) {
                resultDone++;
            }
        }
        if (resultNew == temporaryList.size() || temporaryList.isEmpty()) {
            epic.setStatus(Status.NEW);
        } else if (resultDone == temporaryList.size()) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }
}


