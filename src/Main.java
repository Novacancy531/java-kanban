import assistants.Initialize;
import enums.Status;
import interfaces.TaskManager;
import managers.FileBackedTaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;

@SuppressWarnings("checkstyle:JavadocPackage")
public class Main {


    @SuppressWarnings({"checkstyle:MissingJavadocMethod", "checkstyle:FinalParameters"})
    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager manager = new FileBackedTaskManager(Initialize.initializeStorage());

        Task task1 = new Task("1", "", Status.NEW, Duration.ofMinutes(30),
                LocalDateTime.of(2025, 10, 4, 10, 0));
        Task task2 = new Task("2", "", Status.NEW, Duration.ofMinutes(30),
                LocalDateTime.of(2025, 10, 4, 13, 0));
        Task task3 = new Task("3", "", Status.NEW, Duration.ofMinutes(30),
                LocalDateTime.of(2025, 10, 4, 12, 0));
        Task task4 = new Task("4", "", Status.NEW, Duration.ofMinutes(30),
                LocalDateTime.of(2025, 10, 4, 11, 0));
        /* Epic epic = new Epic("epic", "description");
        Subtask subtask = new Subtask("subtask1", "description", Status.NEW, 1, Duration.ofMinutes(30),
                LocalDateTime.of(2025, 04, 10, 21, 00));

        Subtask subtask1 = new Subtask("subtask1", "description", Status.IN_PROGRESS, 1, Duration.ofMinutes(30),
                LocalDateTime.of(2025, 04, 10, 22, 00));*/
        manager.addTask(task1);
        manager.addTask(task2);
        manager.addTask(task3);
        manager.addTask(task4);

        System.out.println(manager.getTasks());
    }
}
