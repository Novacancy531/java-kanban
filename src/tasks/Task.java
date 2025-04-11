package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import enums.Status;
import enums.TaskType;

public class Task {

    /**
     * Имя задачи.
     */
    private final String name;
    /**
     * Описание задачи.
     */
    private final String description;
    /**
     * Идентификатор задачи.
     */
    private int id;
    /**
     * Статус задачи.
     */
    private Status status;

    /**
     * Время необходимое на выполнение задачи.
     */
    private Duration duration;

    /**
     * Время начала выполнения задачи.
     */
    private LocalDateTime startTime;

    /**
     * Конструктор по умолчанию.
     *
     * @param newName        имя задачи.
     * @param newDescription описание задачи.
     * @param newStatus      статус задачи.
     * @param newDuration    время выполнения задачи.
     * @param newStartTime   время начала выполнения задачи.
     */
    public Task(final String newName, final String newDescription, final Status newStatus,
                final Duration newDuration, final LocalDateTime newStartTime) {
        name = newName;
        description = newDescription;
        status = newStatus;
        duration = newDuration;
        startTime = newStartTime;
    }

    /**
     * @param newName        имя задачи.
     * @param newDescription описание задачи.
     * @param newStatus      статус задачи.
     * @param newId          идентификатор задачи.
     * @param newDuration    время выполнения задачи.
     * @param newStartTime   время начала выполнения задачи.
     */
    public Task(final String newName, final String newDescription, final Status newStatus, final int newId,
                final Duration newDuration, final LocalDateTime newStartTime) {
        name = newName;
        description = newDescription;
        status = newStatus;
        id = newId;
        duration = newDuration;
        startTime = newStartTime;
    }

    /**
     * Возвращает тип задачи значением ENUM.
     *
     * @return тип задачи.
     */
    public TaskType getType() {
        return TaskType.TASK;
    }


    /**
     * Геттер имени.
     *
     * @return имя задачи.
     */
    public String getName() {
        return name;
    }

    /**
     * Геттер описания задачи.
     *
     * @return описание задачи.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Геттер статус задачи.
     *
     * @return статус задачи.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Геттер идентификатор задачи.
     *
     * @return идентификатор задачи.
     */
    public int getId() {
        return id;
    }

    /**
     * Геттер получения времени выполнения задачи.
     *
     * @return время выполнения задачи.
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * Время начала выполнения задачи.
     *
     * @return время начала.
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Сеттер идентификатора задачи.
     *
     * @param newId новый идентификатор задачи.
     */
    public void setId(final int newId) {
        id = newId;
    }

    /**
     * Сеттер статуса задачи.
     *
     * @param newStatus новый статус задачи.
     */
    public void setStatus(final Status newStatus) {
        status = newStatus;
    }

    /**
     * Сеттер времени выполнения.
     *
     * @param duration время выполнения.
     */
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    /**
     * Сеттер времени начала выполнения.
     *
     * @param startTime время начала выполнения.
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Метод получения времени окончания задачи.
     *
     * @return расчетное время окончания выполнения задачи.
     */
    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    /**
     * Сравнение задач по идентификатору.
     *
     * @param object объект для сравнения.
     * @return результат сравнения.
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Task task = (Task) object;
        return id == task.id;
    }

    /**
     * Переопределение hashCode по id.
     *
     * @return Идентификатор задачи.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Переопределение toString().
     *
     * @return представление.
     */
    @Override
    public String toString() {
        return "Task{" +
                "description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", duration=" + duration +
                ", startTime=" + startTime +
                '}' + System.lineSeparator();
    }
}
