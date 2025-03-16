package tasks;

import java.util.Objects;

import enums.Status;

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
     * Конструктор по умолчанию.
     *
     * @param newName        имя задачи.
     * @param newDescription описание задачи.
     * @param newStatus      статус задачи.
     */
    public Task(final String newName, final String newDescription,
                final Status newStatus) {
        name = newName;
        description = newDescription;
        status = newStatus;
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
        return "tasksObjects.Task{" + " name='" + name + '\''
                + ", description='" + description + '\''
                + ", id=" + id + ", status=" + status
                + '}' + System.lineSeparator();
    }
}
