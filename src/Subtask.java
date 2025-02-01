public class Subtask extends Task {

    private int subtaskInEpic;


    Subtask(String name, String description, Status status) {
        super(name, description, status);
    }

    public int getSubtaskInEpic() {
        return subtaskInEpic;
    }

    public void setSubtaskInEpic(int EpicId) {
        this.subtaskInEpic = EpicId;
    }

    @Override
    public String toString() {
        return "Task{" + "name=" + getName() +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                '}';
    }
}
