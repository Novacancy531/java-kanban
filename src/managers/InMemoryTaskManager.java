package managers;

import assistants.PrioritizedTasks;
import enums.Status;
import exceptions.ManagerAddTaskException;
import interfaces.TaskManager;
import interfaces.HistoryManager;
import tasks.Task;
import tasks.Subtask;
import tasks.Epic;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    /**
     * Карта для хранения задач.
     */
    protected final Map<Integer, Task> tasks;
    /**
     * Карта для хранения подзадач.
     */
    protected final Map<Integer, Subtask> subtasks;
    /**
     * Карта для хранения больших задач (с подзадачами).
     */
    protected final Map<Integer, Epic> epics;
    /**
     * Счетчик задач.
     */
    protected int numberOfTasks = 0;
    /**
     * Объект менеджера истории вызова задач.
     */
    private final HistoryManager historyManager;

    /**
     * Объект для отслеживания приоритета задач.
     */
    private final PrioritizedTasks prioritizedTasks;


    /**
     * Конструктор по умолчанию.
     */
    public InMemoryTaskManager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
        historyManager = new InMemoryHistoryManager();
        prioritizedTasks = new PrioritizedTasks();
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
        prioritizedTasks.deleteTasks(tasks);
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
            updateEpicTime(currentEpic);
        }
        subtasks.clear();
        prioritizedTasks.deleteTasks(subtasks);
    }

    /**
     * Метод удаления всех больших задач (включая их подзадачи).
     */
    @Override
    public void removeAllEpics() {
        subtasks.clear();
        epics.clear();
        prioritizedTasks.deleteTasks(subtasks);
    }

    /**
     * Получение задачи по идентификатору.
     *
     * @param id идентификатор.
     * @return задача.
     */
    @Override
    public Task getTaskById(final int id) throws NullPointerException {
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
    public Subtask getSubtaskById(final int id) throws NullPointerException {
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
    public Epic getEpicById(final int id) throws NullPointerException {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    /**
     * Добавление новой задачи.
     *
     * @param task задача.
     */
    @Override
    public void addTask(final Task task) throws ManagerAddTaskException {
        isOverlapping(task);
        newIdForTask(task);
        tasks.put(task.getId(), task);
        prioritizedTasks.addTask(task);
    }

    /**
     * Добавление новой подзадачи.
     *
     * @param subtask подзадача.
     */
    @Override
    public void addSubtask(final Subtask subtask) throws ManagerAddTaskException {
        isOverlapping(subtask);
        newIdForTask(subtask);
        subtasks.put(subtask.getId(), subtask);
        Epic temporaryEpic = epics.get(subtask.getEpicId());
        temporaryEpic.getSubtasksList().add(subtask.getId());
        prioritizedTasks.addTask(subtask);
        updateEpicStatus(temporaryEpic);
        updateEpicTime(temporaryEpic);
    }

    /**
     * Добавление новой большой задачи.
     *
     * @param epic большая задача.
     */
    @Override
    public void addEpic(final Epic epic) {
        newIdForTask(epic);
        epics.put(epic.getId(), epic);
        updateEpicStatus(epic);
        updateEpicTime(epic);
    }

    /**
     * Обновление (замена) задачи.
     *
     * @param task новая задача.
     */
    @Override
    public void updateTask(final Task task) throws ManagerAddTaskException {
        isOverlapping(task);
        if (tasks.containsKey(task.getId())) {
            prioritizedTasks.updateTask(tasks.get(task.getId()), task);
            tasks.replace(task.getId(), task);
        }
    }

    /**
     * Обновление (замена) подзадачи.
     *
     * @param subtask новая подзадача.
     */
    @Override
    public void updateSubtask(final Subtask subtask) throws ManagerAddTaskException {
        isOverlapping(subtask);
        if (subtasks.containsKey(subtask.getId())) {
            Subtask temporarySubtask = subtasks.get(subtask.getId());
            Epic temporaryEpic = epics.get(temporarySubtask.getEpicId());
            prioritizedTasks.updateTask(subtasks.get(subtask.getId()), subtask);
            subtasks.replace(subtask.getId(), subtask);
            updateEpicStatus(temporaryEpic);
            updateEpicTime(temporaryEpic);
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
            updateEpicTime(epic);
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
        prioritizedTasks.deleteTask(tasks.get(id));
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
        prioritizedTasks.deleteTask(subtasks.get(id));
        subtasks.remove(id);
        updateEpicStatus(epic);
        updateEpicTime(epic);
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
        return epic.getSubtasksList().stream().map(subtasks::get).toList();
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
     * Метод для возвращения списка задач отсортированных по времени начала.
     *
     * @return отсортированный список задач.
     */
    @Override
    public Set<Task> getPrioritizedTasks() {
        return prioritizedTasks.getTasks();
    }

    /**
     * Метод проверки пересечения задач по времени выполнения.
     *
     * @param addedTask добавляемая задача.
     */
    @Override
    public void isOverlapping(final Task addedTask) throws ManagerAddTaskException {
        if (prioritizedTasks.getTasks().stream()
                .filter(task -> !task.equals(addedTask))
                .anyMatch(task -> {
                    LocalDateTime addedStart = addedTask.getStartTime();
                    LocalDateTime addedEnd = addedTask.getEndTime();
                    LocalDateTime currentStart = task.getStartTime();
                    LocalDateTime currentEnd = task.getEndTime();

                    return (currentStart.isBefore(addedEnd) && currentEnd.isAfter(addedStart));
                })) {
            throw new ManagerAddTaskException("Время обновляемой задачи занято другой задачей.");
        }
    }

    /**
     * Присваивает задаче новый идентификатор и увеличивает счетчик numberOfTasks на 1.
     *
     * @param task добавляемая задача.
     */
    @Override
    public void newIdForTask(final Task task) {
        task.setId(++numberOfTasks);
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

    /**
     * Обновление времени начала, выполнения и завершения большой задачи.
     *
     * @param epic большая задача.
     */
    @Override
    public void updateEpicTime(final Epic epic) {
        LocalDateTime startTime = LocalDateTime.MAX;
        Duration duration = Duration.ofMinutes(0);

        LocalDateTime startTimeLastTask = LocalDateTime.MIN;
        Duration lastTaskDuration = Duration.ofMinutes(0);

        for (int id : epic.getSubtasksList()) {
            if (subtasks.get(id).getStartTime().isBefore(startTime)) {
                startTime = subtasks.get(id).getStartTime();
            }
            if (subtasks.get(id).getStartTime().isAfter(startTimeLastTask)) {
                startTimeLastTask = subtasks.get(id).getStartTime();
                lastTaskDuration = subtasks.get(id).getDuration();
            }
            duration = duration.plusMinutes(subtasks.get(id).getDuration().toMinutes());
        }

        epic.setStartTime(startTime);
        epic.setDuration(duration);
        epic.setEndTime(startTimeLastTask.plus(lastTaskDuration));
    }
}
