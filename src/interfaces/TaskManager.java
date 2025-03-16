package interfaces;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.List;

public interface TaskManager {

    /**
     * Метод для получения задачи.
     *
     * @return задача(Task).
     */
    List<Task> getTasks();

    /**
     * Метод для получения подзадачи.
     *
     * @return подзадача(Subtask).
     */
    List<Task> getSubtasks();

    /**
     * Метод для получения большой задачи.
     *
     * @return большая задача(Epic).
     */
    List<Task> getEpics();

    /**
     * Удаление всех задач из карты задач.
     */
    void removeAllTasks();

    /**
     * Удаление всех подзадач из карты подзадач.
     */
    void removeAllSubtasks();

    /**
     * Удаление всех больших задач из карты больших задач.
     */
    void removeAllEpics();

    /**
     * * Получение задачи по идентификатору.
     *
     * @param id идентификатор
     * @return задача.
     */
    Task receivingTaskById(int id);

    /**
     * Получение подзадачи по идентификатору.
     *
     * @param id идентификатор
     * @return подзадача.
     */
    Subtask receivingSubtaskById(int id);

    /**
     * Получение большой задачи по идентификатору.
     *
     * @param id идентификатор
     * @return большая задача.
     */
    Epic receivingEpicById(int id);

    /**
     * Добавление задачи в карту задач.
     *
     * @param task задача.
     */
    void addTask(Task task);

    /**
     * Добавление подзадачи в карту подзадач.
     *
     * @param subtask подзадача.
     */
    void addSubtask(Subtask subtask);

    /**
     * Добавление большой задачи в карту задач.
     *
     * @param epic большая задача.
     */
    void addEpic(Epic epic);

    /**
     * Обновление задачи (замещение).
     *
     * @param task новая задача.
     */
    void updateTask(Task task);

    /**
     * Обновление подзадачи (замещение).
     *
     * @param subtask новая подзадача.
     */
    void updateSubtask(Subtask subtask);

    /**
     * Обновление большой задачи (замещение).
     *
     * @param epic новая большая задача.
     */
    void updateEpic(Epic epic);

    /**
     * Удаление задачи по идентификатору.
     *
     * @param id идентификатор.
     */
    void deleteTaskById(int id);

    /**
     * Удаление подзадачи по идентификатору.
     *
     * @param id идентификатор.
     */
    void deleteSubtaskById(int id);

    /**
     * Удаление большой задачи по идентификатору.
     *
     * @param id идентификатор.
     */
    void deleteEpicById(int id);

    /**
     * Получение подзадач большой задачи.
     *
     * @param epic большая задача.
     * @return список подзадач большой задачи.
     */
    List<Subtask> getSubtasksFromEpic(Epic epic);

    /**
     * Обновление статуса большой задачи.
     *
     * @param epic большая задача.
     */
    void updateEpicStatus(Epic epic);

    /**
     * Получение списка истории вызова задач всех типов.
     *
     * @return список истории задач.
     */
    List<Task> getHistory();
}
