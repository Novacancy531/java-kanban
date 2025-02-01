import java.util.HashMap;

public class Epic extends Task {

    private HashMap<Integer, Subtask> epicTasks;

    Epic(String name, String description) {
        super(name, description, Status.NEW);
        epicTasks = new HashMap<>();
    }

    HashMap outputAllSubtasks() {
        return epicTasks;
    }

    void addSubtask(Subtask subtask) {
        epicTasks.put(subtask.getId(), subtask);
        subtask.setSubtaskInEpic(getId());
        setStatus(checkEpicStatus());
    }

    void updateSubtask(int id, Subtask subtask) {
        epicTasks.replace(id, subtask);
        setStatus(checkEpicStatus());
    }

    void removeSubtask(int id) {
        epicTasks.remove(id);
        setStatus(checkEpicStatus());
    }

    void removeAllSubtask() {
        epicTasks.clear();
        setStatus(checkEpicStatus());
    }

    Status checkEpicStatus() {
        int resultDone = 0;
        int resultNew = 0;
        for (Subtask currentSubtask : epicTasks.values()) {
            if (currentSubtask.getStatus() == Status.DONE) {
                resultDone++;
            } else if (currentSubtask.getStatus() == Status.NEW) {
                resultNew++;
            }
        }

        if (resultDone == epicTasks.size()) {
            return Status.DONE;
        } else if (resultNew == epicTasks.size() || epicTasks.isEmpty()) {
            return Status.NEW;
        } else {
            return Status.IN_PROGRESS;
        }
    }

    public HashMap<Integer, Subtask> getEpicTasks() {
        return epicTasks;
    }

    @Override
    public String toString() {
        return "Epic{" + "ID=" + hashCode() + ", name=" + getName() + ", description=" + getDescription() +
                ", status=" + getStatus() +
                ", epicTasks=" + epicTasks +
                '}';
    }

}