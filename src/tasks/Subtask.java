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
     * @param epicId   идентификатор большой задачи данной задачи.
     */
    public Subtask(final String name, final String description, final Status status, final int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    /**
     * @param name        имя задачи.
     * @param description описание задачи.
     * @param status      статус задачи.
     * @param epicId   идентификатор большой задачи данной задачи.
     * @param id          идентификатор задачи.
     */
    public Subtask(final String name, final String description, final Status status, final int epicId,
                   final int id) {
        super(name, description, status, id);
        this.epicId = epicId;
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
