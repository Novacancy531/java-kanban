package test;

import interfaces.TaskManager;
import managers.InMemoryTaskManager;
import enums.Status;
import tasks.Task;
import tasks.Subtask;
import tasks.Epic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class InMemoryTaskManagerTest {

    /**
     * Поле менеджера истории.
     */
    private TaskManager taskManager;
    /**
     * Задача 1.
     */
    private Task task1;
    /**
     * Задача 2.
     */
    private Task task2;
    /**
     * Большая задача 1.
     */
    private Epic epic;
    /**
     * Подзадача 1.
     */
    private Subtask subtask1;
    /**
     * Подзадача 2.
     */
    private Subtask subtask2;


    @BeforeEach
    public void setUp() {
        taskManager = new InMemoryTaskManager();
        task1 = new Task("1", "Задача 1", Status.NEW);
        task2 = new Task("2", "Задача 2", Status.NEW);
        epic = new Epic("3", "Большая задача 3");
        subtask1 = new Subtask("4", "Задача 4", Status.NEW, 1);
        subtask2 = new Subtask("5", "Задача 5", Status.NEW, 1);
    }


    @Test
    void getTasks() {
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        List<Task> reference = new ArrayList<>();
        reference.add(task1);
        reference.add(task2);

        Assertions.assertEquals(2, taskManager.getTasks().size(),
                "Ожидалось другое количество задач.");
        Assertions.assertEquals(reference, taskManager.getTasks(), "Список отличается от эталонного");
    }

    @Test
    void getSubtasks() {
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        List<Task> reference = new ArrayList<>();
        reference.add(subtask1);
        reference.add(subtask2);

        Assertions.assertEquals(2, reference.size(), "Ожидалось другое количество подзадач");
        Assertions.assertEquals(reference, taskManager.getSubtasks(), "Список отличается от эталонного");
    }

    @Test
    void getEpics() {
        taskManager.addEpic(epic);
        List<Task> reference = new ArrayList<>();

        reference.add(epic);

        Assertions.assertEquals(reference, taskManager.getEpics(), "Эпик добавился не верно");
    }

    @Test
    void removeAllTasks() {
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        taskManager.removeAllTasks();

        Assertions.assertTrue(taskManager.getTasks().isEmpty(), "Список задач не пуст");
    }

    @Test
    void removeAllSubtasks() {
        taskManager.addEpic(epic);

        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.removeAllSubtasks();

        Assertions.assertTrue(taskManager.getSubtasks().isEmpty(), "Список подзадач не пустой");
        Assertions.assertTrue(taskManager.getSubtasksFromEpic(epic).isEmpty(),
                "Задачи остались в списке большой задачи");
    }

    @Test
    void removeAllEpics() {
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        taskManager.removeAllEpics();

        Assertions.assertTrue(taskManager.getEpics().isEmpty(), "Список эпиков не пуст");
        Assertions.assertTrue(taskManager.getSubtasks().isEmpty(), "Список подзадач не пуст");
    }

    @Test
    void receivingById() {
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask1);

        Assertions.assertEquals(1, taskManager.receivingEpicById(1).getId(),
                "id большой задачи не равен");
        Assertions.assertEquals(2, taskManager.receivingSubtaskById(2).getId(),
                "id подзадачи не верный");
    }

    @Test
    void addTask() {
        taskManager.addTask(task1);

        Assertions.assertEquals(task1, taskManager.receivingTaskById(1), "Задача добавилась неверно");
    }

    @Test
    void addSubtask() {
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask1);
        int subtaskId = epic.getSubtasksList().getFirst();


        Assertions.assertEquals(subtask1, taskManager.receivingSubtaskById(2), "Подзадача добавилась неверно");
        Assertions.assertEquals(2, subtaskId, "Неверно добавился номер подзадачи в большую задачу");
    }

    @Test
    void addEpic() {
        taskManager.addEpic(epic);

        Assertions.assertEquals(epic, taskManager.receivingEpicById(1), "Большая задача добавилась неверно");
    }

    @Test
    void updateTask() {
        taskManager.addTask(task1);
        task1.setStatus(Status.IN_PROGRESS);
        taskManager.updateTask(task1);

        Assertions.assertEquals(task1, taskManager.receivingTaskById(1), "Задача обновилась неверно");
    }

    @Test
    void updateSubtask() {
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask1);
        subtask1.setStatus(Status.IN_PROGRESS);
        taskManager.updateSubtask(subtask1);

        Assertions.assertEquals(subtask1, taskManager.receivingSubtaskById(2), "Подзадача обновилась неверно");

    }

    @Test
    void updateEpic() {
        taskManager.addEpic(epic);
        Epic tempEpic = new Epic("2", "Задача 2");
        tempEpic.setId(1);
        taskManager.updateEpic(tempEpic);

        Assertions.assertEquals(1, taskManager.getEpics().size(),
                "Задача не обновилась");
        Assertions.assertEquals(tempEpic, taskManager.getEpics().getFirst(), "Задача не обновилась");
    }

    @Test
    void deleteById() {
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask1);
        taskManager.addTask(task1);

        taskManager.deleteTaskById(task1.getId());
        taskManager.deleteSubtaskById(subtask1.getId());
        taskManager.deleteEpicById(epic.getId());

        Assertions.assertFalse(taskManager.getTasks().contains(epic), "Задача не удалилась");
        Assertions.assertFalse(taskManager.getSubtasks().contains(subtask1), "Подзадача не удалилась");
        Assertions.assertFalse(taskManager.getEpics().contains(task1), "Большая задача не удалилась");
    }

    @Test
    void getSubtasksFromEpic() {
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        List<Integer> reference = new ArrayList<>(List.of(subtask1.getId(), subtask2.getId()));

        Assertions.assertEquals(reference, epic.getSubtasksList(), "Списки не равны");
    }

    @Test
    void updateEpicStatus() {
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        subtask1.setStatus(Status.DONE);
        taskManager.updateSubtask(subtask1);

        Assertions.assertEquals(Status.IN_PROGRESS, epic.getStatus(), "Неверный статус");
    }

    @Test
    void equalityOfTasksBYiD() {
        task1.setId(1);
        task2.setId(1);

        Assertions.assertEquals(task1, task2, "Задачи не равны");
    }

    @Test
    void taskEqualsFromManager() {
        taskManager.addTask(task1);
        Task outputTask = taskManager.receivingTaskById(1);

        Assertions.assertEquals(task1.getName(), outputTask.getName(), "Имена не равны");
        Assertions.assertEquals(task1.getDescription(), outputTask.getDescription(), "Описание не равно");
        Assertions.assertEquals(task1.getStatus(), outputTask.getStatus(), "Статусы не равны");
        Assertions.assertEquals(task1.getId(), outputTask.getId(), "Идентификаторы  не равны");

    }
}
