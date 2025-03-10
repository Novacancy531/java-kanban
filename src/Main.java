import managers.*;
import enums.*;
import tasks.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        Task taskFirst = new Task("Завтрак", "Позавтракать", Status.DONE);
        Task taskSecond = new Task("Обед", "Пообедать", Status.IN_PROGRESS);
        Epic epicFirst = new Epic("Ужин", "Поужинать");
        Subtask subtaskFirst = new Subtask("Есть", "Есть гречку", Status.DONE, 3);
        Subtask subtaskSecond = new Subtask("Пить", "Пить компот", Status.IN_PROGRESS, 3);
        Epic epicSecond = new Epic("Полдник", "Полдничать");
        Subtask subtaskThird = new Subtask("Есть", "Есть печенье", Status.IN_PROGRESS, 6);

        InMemoryTaskManager manager = new InMemoryTaskManager();

        manager.addTask(taskFirst);
        manager.addTask(taskSecond);
        manager.addEpic(epicFirst);
        manager.addSubtask(subtaskFirst);
        manager.addSubtask(subtaskSecond);
        manager.addEpic(epicSecond);
        manager.addSubtask(subtaskThird);

        manager.receivingTaskById(2);
        manager.receivingEpicById(6);
        manager.receivingTaskById(1);
        System.out.println(manager.getHistory());

        manager.receivingSubtaskById(4);
        manager.receivingSubtaskById(5);

        System.out.println(manager.getHistory());
    }
}
