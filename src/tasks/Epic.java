package tasks;

import enums.Status;
import enums.TaskType;

import java.util.ArrayList;
import java.util.List;


public final class Epic extends Task {

    /**
     * Список подзадач большой задачи.
     */
    private List<Integer> subtasksList;


    /**
     * Конструктор по умолчанию.
     *
     * @param name        имя задачи.
     * @param description описание задачи.
     */
    public Epic(final String name, final String description) {
        super(name, description, Status.NEW);
        subtasksList = new ArrayList<>();
    }

    /**
     * @param name        имя задачи.
     * @param description описание задачи.
     * @param id          идентификатор задачи.
     */
    public Epic(final String name, final String description, final int id) {
        super(name, description, Status.NEW, id);
        subtasksList = new ArrayList<>();
    }

    /**
     * Возвращает тип большой задачи значением ENUM.
     *
     * @return тип Большой задачи.
     */
    @Override
    public TaskType getType() {
        return TaskType.EPIC;
    }

    /**
     * Геттер списка подзадач.
     *
     * @return список подзадач.
     */
    public List<Integer> getSubtasksList() {
        return subtasksList;
    }

    /**
     * Сеттер списка подзадач.
     *
     * @param list список подзадач.
     */
    public void setSubtasksList(final List<Integer> list) {
        this.subtasksList = list;
    }

    @Override
    public String toString() {
        return "tasksObjects.Epic{" + " name=" + getName()
                + ", description='" + getDescription() + '\''
                + ", id=" + getId()
                + ", status=" + getStatus()
                + '}' + System.lineSeparator();
    }
}
