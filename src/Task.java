import java.util.Objects;

public class Task {

    private final String name;
    private final String description;
    private int id;
    private Status status;

    Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
        TaskManager.numberOfTasks++;
        this.id = TaskManager.numberOfTasks;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Task task = (Task) object;
        return Objects.equals(name, task.name) && Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Task{" +
                " name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}' + System.lineSeparator();
    }
}