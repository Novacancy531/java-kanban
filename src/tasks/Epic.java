package tasks;

import enums.Status;
import enums.TaskType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public final class Epic extends Task {

    /**
     * Время завершения большой задачи.
     */
    private LocalDateTime endTime;
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
        super(name, description, Status.NEW, null, null);
        subtasksList = new ArrayList<>();
        endTime = null;
    }

    /**
     * @param name        имя задачи.
     * @param description описание задачи.
     * @param id          идентификатор задачи.
     */
    public Epic(final String name, final String description, final int id) {
        super(name, description, Status.NEW, id, null, null);
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

    /**
     * Геттер времени окончания выполнения.
     *
     * @return время окончания выполнения.
     */
    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Сеттер времени окончания выполнения.
     *
     * @param endTime время окончания выполнения.
     */
    public void setEndTime(final LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Epic{" + " name=" + getName() + '\''
                + ", description='" + getDescription() + '\''
                + ", id=" + getId()
                + ", status=" + getStatus()
                + ", duration=" + getDuration()
                + ", startTime=" + getStartTime()
                + '}' + System.lineSeparator();
    }
}
