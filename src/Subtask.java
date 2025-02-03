public class Subtask extends Task {

    private int EpicId;

    Subtask(String name, String description, Status status) {
        super(name, description, status);
    }

    public int getEpicId() {
        return EpicId;
    }

    public void setEpicId(int epicId) {
        EpicId = epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" + " name=" + getName() +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() +
                '}' + System.lineSeparator();
    }
}
