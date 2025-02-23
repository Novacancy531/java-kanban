package tasks;

import enums.Status;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    private List<Integer> subtasksList;


    public Epic(String name, String description) {
        super(name, description, Status.NEW);
        subtasksList = new ArrayList<>();
    }

    public List<Integer> getSubtasksList() {
        return subtasksList;
    }

    public void setSubtasksList(List<Integer> subtasksList) {
        this.subtasksList = subtasksList;
    }

    @Override
    public String toString() {
        return "tasksObjects.Epic{" + " name=" + getName() +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() +
                '}' + System.lineSeparator();
    }
}


