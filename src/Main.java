import assistants.Initialize;
import enums.Status;
import managers.FileBackedTaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;

@SuppressWarnings("checkstyle:JavadocPackage")
public class Main {


    @SuppressWarnings({"checkstyle:MissingJavadocMethod", "checkstyle:FinalParameters"})
    public static void main(String[] args) {
        System.out.println("Поехали!");

        Task task = new Task("ra", "fa", Status.NEW);
        Epic epic = new Epic("Большая задача", "Описание большой задачи");


        FileBackedTaskManager data = FileBackedTaskManager.loadFromFile(Initialize.initializeStorage());

        Task task1 = new Task("задача44", "Описание", Status.IN_PROGRESS);
        Task task2 = new Task("задача5", "Описание", Status.IN_PROGRESS);

        data.addTask(task);
        data.addEpic(epic);

        Subtask subtask1 = new Subtask("Подзадача", "Описание", Status.DONE, epic.getId());
        Subtask subtask2 = new Subtask("Подзадача2", "Описание2", Status.IN_PROGRESS, epic.getId());

        data.addSubtask(subtask1);
        data.addSubtask(subtask2);
        data.addTask(task1);
        data.addTask(task2);


    }

}
