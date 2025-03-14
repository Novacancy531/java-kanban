package interfaces;

import tasks.Task;

import java.util.List;

public interface HistoryManager {

    /**
     * Метод добавления задачи в историю вызовов.
     * @param task задача из менеджера.
     */
    void add(Task task);

    /**
     * Удаление задачи из истории вызовов.
     * @param id Идентификатор задачи.
     */
    void remove(int id);

    /**
     * Получение списка истории вызовов задач.
     * @return Список задач.
     */
    List<Task> getHistory();
}
