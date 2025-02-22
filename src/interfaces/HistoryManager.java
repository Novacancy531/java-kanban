package interfaces;

import tasksObjects.Task;

import java.util.List;

public interface HistoryManager {


    void add(Task task);

    List<Task> getHistory();
}
