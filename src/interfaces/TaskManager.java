package interfaces;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.List;

public interface TaskManager {

    List<Task> getTasks();

    List<Task> getSubtasks();

    List<Task> getEpics();

    void removeAllTasks();

    void removeAllSubtasks();

    void removeAllEpics();

    Task receivingTaskById(int id);

    Subtask receivingSubtaskById(int id);

    Epic receivingEpicById(int id);

    void addTask(Task task);

    void addSubtask(Subtask subtask);

    void addEpic(Epic epic);

    void updateTask(Task task);

    void updateSubtask(Subtask subtask);

    void updateEpic(Epic epic);

    void deleteTaskById(int id);

    void deleteSubtaskById(int id);

    void deleteEpicById(int id);

    List<Subtask> getSubtasksFromEpic(Epic epic);

    void updateEpicStatus(Epic epic);

    List<Task> getHistory();
}
