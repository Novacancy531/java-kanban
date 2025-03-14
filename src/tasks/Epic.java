package tasks;

import enums.Status;

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
