package tasks;

import enums.Status;
import enums.TaskType;

public final class Subtask extends Task {

    /**
     * Идентификатор большой задачи данной подзадачи.
     */
    private final int epicId;

    /**
     * Конструктор по умолчанию.
     *
     * @param name        имя задачи.
     * @param description описание задачи.
     * @param status      статус задачи.
     * @param newEpicId   идентификатор большой задачи данной задачи.
     */
    public Subtask(final String name, final String description, final Status status, final int newEpicId) {
        super(name, description, status);
        epicId = newEpicId;
    }

    /**
     * @param name        имя задачи.
     * @param description описание задачи.
     * @param status      статус задачи.
     * @param newEpicId   идентификатор большой задачи данной задачи.
     * @param id          идентификатор задачи.
     */
    public Subtask(final String name, final String description, final Status status, final int newEpicId,
                   final int id) {
        super(name, description, status, id);
        epicId = newEpicId;
    }

    /**
     * Возвращает тип подзадачи значением ENUM.
     *
     * @return тип подзадачи.
     */
    @Override
    public TaskType getType() {
        return TaskType.SUBTASK;
    }

    /**
     * Геттер получения идентификатор большой задачи.
     *
     * @return идентификатор большой задачи.
     */
    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "tasksObjects.Subtask{" + " name=" + getName()
                + ", description='" + getDescription() + '\''
                + ", id=" + getId()
                + ", status=" + getStatus() + ", epicId" + getEpicId()
                + '}' + System.lineSeparator();
    }
}
