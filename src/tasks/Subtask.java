package tasks;

import enums.Status;

public final class Subtask extends Task {

    /**
     * Идентификатор большой задачи данной подзадачи.
     */
    private final int epicId;

    /**
     * Конструктор по умолчанию.
     *
     * @param name        имя задачи.
     * @param description описание подзадачи.
     * @param status      статус подзадачи.
     * @param id          идентификатор большой задачи данной задачи.
     */
    public Subtask(final String name, final String description,
                   final Status status, final int id) {
        super(name, description, status);
        this.epicId = id;
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
