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

    public int getId() {
        return id;
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

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
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
        return "Task{" + "ID=" + getId() +
                ", name='" + name + '\'' +
                "description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}