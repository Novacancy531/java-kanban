
import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> subtasksList;


    Epic(String name, String description) {
        super(name, description, Status.NEW);
        subtasksList = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtasksList() {
        return subtasksList;
    }

    public void setSubtasksList(ArrayList<Integer> subtasksList) {
        this.subtasksList = subtasksList;
    }

    Status checkEpicStatus() {
        int resultDone = 0;
        int resultNew = 0;
        for (int id : subtasksList) {
            Subtask subtask = (Subtask) TaskManager.subtasks.get(id);
            if (subtask.getStatus() == Status.DONE) {
                resultDone++;
            }
            if (subtask.getStatus() == Status.NEW) {
                resultNew++;
            }
        }
        if (resultDone == subtasksList.size()) {
            return Status.DONE;
        } else if (resultNew == subtasksList.size() || subtasksList.isEmpty()) {
            return Status.NEW;
        } else {
            return Status.IN_PROGRESS;
        }
    }

    @Override
    public String toString() {
        return "Epic{" + " name=" + getName() +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() +
                '}' + System.lineSeparator();
    }
}

