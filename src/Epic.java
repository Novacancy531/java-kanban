import java.util.HashMap;
import java.util.ArrayList;

public class Epic extends Task {

    ArrayList<Integer> subtasksList = new ArrayList<>();

    Epic(String name, String description) {
        super(name, description, Status.NEW);
        subtasksList = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtasksList() {
        return subtasksList;
    }




    void addSubtask(Subtask subtask) {
        subtasksList.add(subtask.getId());
        subtask.setEpicID(getId());
    }


     void checkEpicStatus() {
        int resultDone = 0;
        int resultNew = 0;
        for (int id : this.subtasksList) {
            Subtask subtask = (Subtask) TaskManager.getSubtasks().get(id);
            if (subtask.getStatus() == Status.DONE) {
                resultDone++;
            } else if (subtask.getStatus() == Status.NEW) {
                resultNew++;
            }
        }

        if (resultDone == subtasksList.size()) {
            this.setStatus(Status.DONE);
        } else if (resultNew == subtasksList.size() || subtasksList.isEmpty()) {
            this.setStatus(Status.NEW);
        } else {
            this.setStatus(Status.IN_PROGRESS);
        }
    }


    @Override
    public String toString() {
        return "Epic{" + "ID=" + hashCode() + ", name=" + getName() + ", description=" + getDescription() +
                ", status=" + getStatus();
    }

}