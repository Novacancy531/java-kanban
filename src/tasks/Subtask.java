package tasks;

import enums.Status;

public class Subtask extends Task {

    private int epicId;


    public Subtask(String name, String description, Status status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "tasksObjects.Subtask{" + " name=" + getName() +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() + ", epicId" + getEpicId() +
                '}' + System.lineSeparator();
    }
}
