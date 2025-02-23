package managers;

import enums.*;
import interfaces.*;
import tasks.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    private final Map<Integer, Task> tasks;
    private final Map<Integer, Subtask> subtasks;
    private final Map<Integer, Epic> epics;
    private int numberOfTasks = 0;
    private final HistoryManager historyManager;


    public InMemoryTaskManager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
        historyManager = new InMemoryHistoryManager();
    }

    // Пункт А: Получение списка всех задач.

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Task> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public List<Task> getEpics() {
        return new ArrayList<>(epics.values());
    }

    // Пункт B: Удаление всех задач.

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public void removeAllSubtasks() {
        for (Subtask subtask : subtasks.values()) {
            Epic currentEpic = epics.get(subtask.getEpicId());
            List<Integer> temporaryList = currentEpic.getSubtasksList();
            temporaryList.clear();
            updateEpicStatus(currentEpic);
        }
        subtasks.clear();
    }

    @Override
    public void removeAllEpics() {
        subtasks.clear();
        epics.clear();
    }

    // Пункт C: Получение по идентификатору.

    @Override
    public Task receivingTaskById(int id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Subtask receivingSubtaskById(int id) {
        historyManager.add(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public Epic receivingEpicById(int id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    // Пункт D: Создание.

    @Override
    public void addTask(Task task) {
        numberOfTasks++;
        task.setId(numberOfTasks);
        tasks.put(task.getId(), task);
    }

    @Override
    public void addSubtask(Subtask subtask) {
        numberOfTasks++;
        subtask.setId(numberOfTasks);
        subtasks.put(subtask.getId(), subtask);
        Epic temporaryEpic = epics.get(subtask.getEpicId());
        temporaryEpic.getSubtasksList().add(subtask.getId());
        updateEpicStatus(temporaryEpic);
    }

    @Override
    public void addEpic(Epic epic) {
        numberOfTasks++;
        epic.setId(numberOfTasks);
        epics.put(epic.getId(), epic);
        updateEpicStatus(epic);
    }

    // Пункт E: Обновление.

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.replace(task.getId(), task);
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            Subtask temporarySubtask = subtasks.get(subtask.getId());
            Epic temporaryEpic = epics.get(temporarySubtask.getEpicId());
            subtasks.replace(subtask.getId(), subtask);
            updateEpicStatus(temporaryEpic);

        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            Epic temporaryEpic = epics.get(epic.getId());
            epic.setSubtasksList(temporaryEpic.getSubtasksList());
            epics.replace(epic.getId(), epic);
            updateEpicStatus(epic);
        }
    }

    // Пункт F: Удаление по идентификатору.

    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        Epic epic = epics.get(subtask.getEpicId());
        epic.getSubtasksList().remove(epic.getSubtasksList().indexOf(id));
        subtasks.remove(id);
        updateEpicStatus(epic);
    }

    @Override
    public void deleteEpicById(int id) {
        Epic epic = epics.get(id);
        List<Integer> epicSubtasks = epic.getSubtasksList();
        for (int taskId : epicSubtasks) {
            subtasks.remove(taskId);
        }
        epics.remove(id);
    }


    // Пункт 3A: Получение списка подзадач эпика.

    @Override
    public List<Subtask> getSubtasksFromEpic(Epic epic) {
        ArrayList<Subtask> temporaryOutputList = new ArrayList<>();
        for (int subtaskId : epic.getSubtasksList()) {
            temporaryOutputList.add(subtasks.get(subtaskId));
        }
        return temporaryOutputList;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
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


