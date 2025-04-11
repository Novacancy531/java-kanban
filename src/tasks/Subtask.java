package tasks;

import enums.Status;
import enums.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;

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
     * @param epicId      идентификатор большой задачи данной задачи.
     * @param duration    время выполнения задачи.
     * @param startTime   время начала выполнения задачи.
     */
    public Subtask(final String name, final String description, final Status status, final int epicId,
                   final Duration duration, final LocalDateTime startTime) {
        super(name, description, status, duration, startTime);
        this.epicId = epicId;
    }

    /**
     * @param name        имя задачи.
     * @param description описание задачи.
     * @param status      статус задачи.
     * @param epicId      идентификатор большой задачи данной задачи.
     * @param id          идентификатор задачи.
     * @param duration    время выполнения задачи.
     * @param startTime   время начала выполнения задачи.
     */
    public Subtask(final String name, final String description, final Status status, final int epicId,
                   final int id, final Duration duration, final LocalDateTime startTime) {
        super(name, description, status, id, duration, startTime);
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
        return "Subtask{" +
                "description='" + getDescription() + '\'' +
                ", name='" + getName() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() +
                ", epicId=" + getEpicId() +
                ", duration=" + getDuration() +
                ", startTime=" + getStartTime() +
                '}' + System.lineSeparator();
    }
}
