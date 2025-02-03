public class Subtask extends Task {

     int EpicID;

     Subtask(String name, String description, Status status, Epic epic) {
        super(name, description, status);
    }

    public void setEpicID(int EpicId) {
        this.EpicID = EpicId;
    }

    public int getEpicID() {
        return EpicID;
    }

    @Override
    public String toString() {
        return "Subtask{" + "name=" + getName() +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                '}';
    }
}
