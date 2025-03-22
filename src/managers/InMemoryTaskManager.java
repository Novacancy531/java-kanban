package managers;

import enums.Status;
import interfaces.TaskManager;
import interfaces.HistoryManager;
import tasks.Task;
import tasks.Subtask;
import tasks.Epic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    /**
     * Карта для хранения задач.
     */
    private final Map<Integer, Task> tasks;
    /**
     * Карта для хранения подзадач.
     */
    private final Map<Integer, Subtask> subtasks;
    /**
     * Карта для хранения больших задач (с подзадачами).
     */
    private final Map<Integer, Epic> epics;
    /**
     * Счетчик задач.
     */
    private int numberOfTasks = 0;
    /**
     * Объект менеджера истории вызова задач.
     */
    private final HistoryManager historyManager;


    /**
     * Конструктор по умолчанию.
     */
    public InMemoryTaskManager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
        historyManager = new InMemoryHistoryManager();
    }

    /**
     * Геттер получения карты с задачами.
     *
     * @return карта задач.
     */
    public Map<Integer, Task> getTaskMap() {
        return tasks;
    }

    /**
     * Геттер получения карты с подзадачами.
     *
     * @return карта подзадач.
     */
    public Map<Integer, Subtask> getSubtaskMap() {
        return subtasks;
    }

    /**
     * Геттер получения карты больших задач.
     *
     * @return карта больших задач.
     */
    public Map<Integer, Epic> getEpicMap() {
        return epics;
    }

    /**
     * Метод получения списка задач.
     *
     * @return список задач.
     */
    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    /**
     * Метод получения списка подзадач.
     *
     * @return список подзадач.
     */
    @Override
    public List<Task> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    /**
     * Метод получения списка больших задач.
     *
     * @return список больших задач.
     */
    @Override
    public List<Task> getEpics() {
        return new ArrayList<>(epics.values());
    }

    /**
     * Метод удаления всех задач.
     */
    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    /**
     * Метод удаления всех подзадач.
     */
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

    /**
     * Метод удаления всех больших задач (включая их подзадачи).
     */
    @Override
    public void removeAllEpics() {
        subtasks.clear();
        epics.clear();
    }

    /**
     * Получение задачи по идентификатору.
     *
     * @param id идентификатор.
     * @return задача.
     */
    @Override
    public Task receivingTaskById(final int id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    /**
     * Получение подзадачи по идентификатору.
     *
     * @param id идентификатор.
     * @return подзадача.
     */
    @Override
    public Subtask receivingSubtaskById(final int id) {
        historyManager.add(subtasks.get(id));
        return subtasks.get(id);
    }

    /**
     * Получение большой задачи по идентификатору.
     *
     * @param id идентификатор.
     * @return большая задача.
     */
    @Override
    public Epic receivingEpicById(final int id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    /**
     * Добавление новой задачи.
     *
     * @param task задача.
     */
    @Override
    public void addTask(final Task task) {
        numberOfTasks++;
        task.setId(numberOfTasks);
        tasks.put(task.getId(), task);
    }

    /**
     * Добавление новой подзадачи.
     *
     * @param subtask подзадача.
     */
    @Override
    public void addSubtask(final Subtask subtask) {
        numberOfTasks++;
        subtask.setId(numberOfTasks);
        subtasks.put(subtask.getId(), subtask);
        Epic temporaryEpic = epics.get(subtask.getEpicId());
        temporaryEpic.getSubtasksList().add(subtask.getId());
        updateEpicStatus(temporaryEpic);
    }

    /**
     * Добавление новой большой задачи.
     *
     * @param epic большая задача.
     */
    @Override
    public void addEpic(final Epic epic) {
        numberOfTasks++;
        epic.setId(numberOfTasks);
        epics.put(epic.getId(), epic);
        updateEpicStatus(epic);
    }

    /**
     * Обновление (замена) задачи.
     *
     * @param task новая задача.
     */
    @Override
    public void updateTask(final Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.replace(task.getId(), task);
        }
    }

    /**
     * Обновление (замена) подзадачи.
     *
     * @param subtask новая подзадача.
     */
    @Override
    public void updateSubtask(final Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            Subtask temporarySubtask = subtasks.get(subtask.getId());
            Epic temporaryEpic = epics.get(temporarySubtask.getEpicId());
            subtasks.replace(subtask.getId(), subtask);
            updateEpicStatus(temporaryEpic);

        }
    }

    /**
     * Обновление (замена) большой задачи.
     *
     * @param epic новая большая задача.
     */
    @Override
    public void updateEpic(final Epic epic) {
        if (epics.containsKey(epic.getId())) {
            Epic temporaryEpic = epics.get(epic.getId());
            epic.setSubtasksList(temporaryEpic.getSubtasksList());
            epics.replace(epic.getId(), epic);
            updateEpicStatus(epic);
        }
    }

    /**
     * Удаление задачи по идентификатору.
     *
     * @param id идентификатор.
     */
    @Override
    public void deleteTaskById(final int id) {
        historyManager.remove(id);
        tasks.remove(id);
    }

    /**
     * Удаление подзадачи по идентификатору.
     *
     * @param id идентификатор.
     */
    @Override
    public void deleteSubtaskById(final int id) {
        Subtask subtask = subtasks.get(id);
        Epic epic = epics.get(subtask.getEpicId());
        epic.getSubtasksList().remove((Integer) id);
        historyManager.remove(id);
        subtasks.remove(id);
        updateEpicStatus(epic);
    }

    /**
     * Удаление большой задачи по идентификатору.
     *
     * @param id идентификатор.
     */
    @Override
    public void deleteEpicById(final int id) {
        Epic epic = epics.get(id);
        List<Integer> epicSubtasks = epic.getSubtasksList();
        for (int taskId : epicSubtasks) {
            subtasks.remove(taskId);
            historyManager.remove(taskId);
        }
        historyManager.remove(id);
        epics.remove(id);
    }

    /**
     * Получение списка всех подзадач входящих в данную большую задачу.
     *
     * @param epic большая задача.
     * @return список подзадач.
     */
    @Override
    public List<Subtask> getSubtasksFromEpic(final Epic epic) {
        ArrayList<Subtask> temporaryOutputList = new ArrayList<>();
        for (int subtaskId : epic.getSubtasksList()) {
            temporaryOutputList.add(subtasks.get(subtaskId));
        }
        return temporaryOutputList;
    }

    /**
     * Получение списка истории просмотров задач всех типов.
     *
     * @return список просмотров.
     */
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    /**
     * Обновление статуса большой задачи на основании статусов её подзадач.
     *
     * @param epic большая задача.
     */
    @Override
    public void updateEpicStatus(final Epic epic) {
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
