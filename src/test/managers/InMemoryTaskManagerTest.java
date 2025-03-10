package test.managers;

import managers.InMemoryTaskManager;
import enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.*;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

class InMemoryTaskManagerTest {

    public InMemoryTaskManager manager;


    @BeforeEach
    public void beforeEach() {

        manager = new InMemoryTaskManager();
    }


    @Test
    void getTasks() {
        ArrayList<Task> list = new ArrayList<>();

        Task taskFirst = new Task("Завтрак", "Позавтракать", Status.DONE);
        Task taskSecond = new Task("Обед", "Пообедать", Status.IN_PROGRESS);
        taskFirst.setId(1);
        taskSecond.setId(2);
        list.add(taskFirst);
        list.add(taskSecond);

        manager.addTask(new Task("Завтрак", "Позавтракать", Status.DONE));
        manager.addTask(new Task("Обед", "Пообедать", Status.IN_PROGRESS));

        Assertions.assertEquals(list, manager.getTasks(), "Списки не равны");
    }

    @Test
    void getSubtasks() {
        ArrayList<Task> list = new ArrayList<>();

        Epic epicFirst = new Epic("Ужин", "Поужинать");
        Subtask subtaskFirst = new Subtask("Есть", "Есть гречку", Status.DONE, 3);
        Subtask subtaskSecond = new Subtask("Пить", "Пить компот", Status.IN_PROGRESS, 3);
        subtaskFirst.setId(2);
        subtaskSecond.setId(3);
        list.add(subtaskFirst);
        list.add(subtaskSecond);

        manager.addEpic(epicFirst);
        manager.addSubtask(new Subtask("Есть", "Есть гречку", Status.DONE, 1));
        manager.addSubtask(new Subtask("Пить", "Пить компот", Status.IN_PROGRESS, 1));

        Assertions.assertEquals(list, manager.getSubtasks(), "Списки не равны");
    }

    @Test
    void getEpics() {
        ArrayList<Epic> list = new ArrayList<>();
        Epic epicFirst = new Epic("Ужин", "Поужинать");
        epicFirst.setId(1);
        list.add(epicFirst);

        manager.addEpic(new Epic("Ужин", "Поужинать"));

        Assertions.assertEquals(list, manager.getEpics());
    }

    @Test
    void removeAllTasks() {

        manager.addTask(new Task("Завтрак", "Позавтракать", Status.DONE));
        manager.addTask(new Task("Обед", "Пообедать", Status.IN_PROGRESS));
        manager.removeAllTasks();

        Assertions.assertTrue(manager.getTasks().isEmpty(), "Список задач не пустой");
    }

    @Test
    void removeAllSubtasks() {

        Epic epicFirst = new Epic("Ужин", "Поужинать");
        epicFirst.setId(1);
        manager.addEpic(epicFirst);
        manager.addSubtask(new Subtask("Есть", "Есть гречку", Status.DONE, 1));
        manager.addSubtask(new Subtask("Пить", "Пить компот", Status.IN_PROGRESS, 1));
        manager.removeAllSubtasks();

        Assertions.assertTrue(manager.getSubtasks().isEmpty(), "Список подзадач не пустой");
        Assertions.assertTrue(manager.getSubtasksFromEpic(epicFirst).isEmpty(), "Остались в списке эпика");
    }

    @Test
    void removeAllEpics() {

        manager.addEpic(new Epic("Ужин", "Поужинать"));
        manager.addSubtask(new Subtask("Есть", "Есть гречку", Status.DONE, 1));
        manager.addSubtask(new Subtask("Пить", "Пить компот", Status.IN_PROGRESS, 1));

        manager.removeAllEpics();

        Assertions.assertTrue(manager.getEpics().isEmpty(), "Список эпиков не пуст");
        Assertions.assertTrue(manager.getSubtasks().isEmpty(), "Список подзадач не пуст");
    }

    @Test
    void receivingById() {

        manager.addTask(new Task("Завтрак", "Позавтракать", Status.DONE));
        manager.addEpic(new Epic("Ужин", "Поужинать"));
        manager.addSubtask(new Subtask("Есть", "Есть гречку", Status.DONE, 2));

        Assertions.assertEquals(1, manager.receivingTaskById(1).getId(), "id задач не равен");
        Assertions.assertEquals(2, manager.receivingEpicById(2).getId(), "id 'эпика не верный");
        Assertions.assertEquals(3, manager.receivingSubtaskById(3).getId(), "id подзадач не верный");
    }

    @Test
    void addTask() {

        Task firstTask = new Task("Завтрак", "Позавтракать", Status.DONE);
        firstTask.setId(1);
        manager.addTask(new Task("Завтрак", "Позавтракать", Status.DONE));

        Assertions.assertEquals(manager.receivingTaskById(1), firstTask, "Задача добавилась неверно");
    }

    @Test
    void addSubtask() {

        Epic epicFirst = new Epic("Ужин", "Поужинать");
        Subtask subtaskFirst = new Subtask("Есть", "Есть гречку", Status.DONE, 1);

        epicFirst.setId(1);
        subtaskFirst.setId(2);

        manager.addEpic(new Epic("Ужин", "Поужинать"));
        manager.addSubtask(new Subtask("Есть", "Есть гречку", Status.DONE, 1));
        int subtaskId = manager.receivingEpicById(1).getSubtasksList()
                .get(manager.receivingEpicById(1).getSubtasksList().indexOf(2));

        Assertions.assertEquals(subtaskFirst, manager.receivingSubtaskById(2), "Подзадача добавилась неверно");
        Assertions.assertEquals(2, subtaskId, "Неверно добавился номер подзадачи в эпик");
    }

    @Test
    void addEpic() {

        Epic epicFirst = new Epic("Ужин", "Поужинать");
        epicFirst.setId(1);
        manager.addEpic(new Epic("Ужин", "Поужинать"));

        Assertions.assertEquals(epicFirst, manager.receivingEpicById(1), "Эпик добавился неверно");
    }

    @Test
    void updateTask() {

        Task firstTask = new Task("Завтрак", "Позавтракать", Status.DONE);
        firstTask.setId(1);

        manager.addTask(new Task("Завтрак", "Позавтракать", Status.IN_PROGRESS));
        manager.updateTask(firstTask);

        Assertions.assertEquals(firstTask, manager.receivingTaskById(1), "Задача обновилась неверно");
    }

    @Test
    void updateSubtask() {

        Subtask subtaskFirst = new Subtask("Есть", "Есть гречку", Status.DONE, 1);
        subtaskFirst.setId(2);

        manager.addEpic(new Epic("Ужин", "Поужинать"));
        manager.addSubtask(new Subtask("Есть", "Есть гречку", Status.NEW, 1));
        manager.updateSubtask(subtaskFirst);

        Assertions.assertEquals(subtaskFirst, manager.receivingSubtaskById(2), "Подзадача обновилась неверно");

    }

    @Test
    void updateEpic() {

        Epic epicFirst = new Epic("Ужин", "Поужинать");
        epicFirst.setId(1);

        manager.addEpic(new Epic("Полдник", "Полдничать"));
        manager.updateEpic(epicFirst);

        Assertions.assertEquals(epicFirst, manager.receivingEpicById(1), "Эпик обновился неверно");
    }

    @Test
    void deleteById() {

        Task taskFirst = new Task("Завтрак", "Позавтракать", Status.DONE);
        Epic epicFirst = new Epic("Ужин", "Поужинать");
        Subtask subtaskFirst = new Subtask("Есть", "Есть гречку", Status.DONE, 2);

        manager.addTask(taskFirst);
        manager.addEpic(epicFirst);
        manager.addSubtask(subtaskFirst);
        manager.deleteTaskById(1);
        manager.deleteSubtaskById(3);
        manager.deleteEpicById(2);
        Assertions.assertNull(manager.receivingTaskById(1), "Задача не удалилась");
        Assertions.assertNull(manager.receivingSubtaskById(2), "Подзадача не удалилась");
        Assertions.assertNull(manager.receivingEpicById(3), "Эпик не удалился");
    }

    @Test
    void getSubtasksFromEpic() {

        Epic epicFirst = new Epic("Ужин", "Поужинать");
        Subtask subtaskFirst = new Subtask("Есть", "Есть гречку", Status.DONE, 1);
        Subtask subtaskSecond = new Subtask("Пить", "Пить компот", Status.IN_PROGRESS, 1);
        subtaskFirst.setId(2);
        subtaskSecond.setId(3);

        List<Integer> temp = new ArrayList<>();
        temp.add(2);
        temp.add(3);

        manager.addEpic(epicFirst);
        manager.addSubtask(subtaskFirst);
        manager.addSubtask(subtaskSecond);

        Assertions.assertEquals(temp, manager.receivingEpicById(1).getSubtasksList(), "Списки не равны");
    }

    @Test
    void updateEpicStatus() {
        Epic epicFirst = new Epic("Ужин", "Поужинать");
        Subtask subtaskFirst = new Subtask("Есть", "Есть гречку", Status.NEW, 1);
        Subtask subtaskSecond = new Subtask("Есть", "Есть гречку", Status.DONE, 1);
        subtaskSecond.setId(2);

        manager.addEpic(epicFirst);
        manager.addSubtask(subtaskFirst);
        manager.updateSubtask(subtaskSecond);

        Assertions.assertEquals(Status.DONE, manager.receivingEpicById(1).getStatus(), "Неверный статус");
    }

    @Test
    void equalityOfTasksBYiD() {

        Task taskFirst = new Task("Завтрак", "Позавтракать", Status.DONE);
        Task taskSecond = new Task("Обед", "Пообедать", Status.IN_PROGRESS);
        taskFirst.setId(1);
        taskSecond.setId(1);

        Epic epicFirst = new Epic("Ужин", "Поужинать");
        Epic epicSecond = new Epic("Полдник", "Полдничать");
        epicFirst.setId(2);
        epicSecond.setId(2);

        Subtask subtaskFirst = new Subtask("Есть", "Есть гречку", Status.DONE, 2);
        Subtask subtaskSecond = new Subtask("Пить", "Пить компот", Status.IN_PROGRESS, 2);
        subtaskFirst.setId(3);
        subtaskSecond.setId(3);

        Assertions.assertTrue(taskFirst.equals(taskSecond), "Задачи не равны");
        Assertions.assertTrue(epicFirst.equals(epicSecond), "Эпики не равны");
        Assertions.assertTrue(subtaskFirst.equals(subtaskSecond), "Подзадачи не равны");
    }

    @Test
    void taskEqualsFromManager() {

        Task taskFirst = new Task("Завтрак", "Позавтракать", Status.DONE);
        taskFirst.setId(1);

        manager.addTask(taskFirst);

        Assertions.assertEquals(taskFirst.getName(), manager.receivingTaskById(1).getName(), "Имена не равны");
        Assertions.assertEquals(taskFirst.getDescription(), manager.receivingTaskById(1).getDescription(),
                "Описание не равно");
        Assertions.assertEquals(taskFirst.getStatus(), manager.receivingTaskById(1).getStatus(),
                "Статусы не равны");
        Assertions.assertEquals(taskFirst.getId(), manager.receivingTaskById(1).getId(), "id не равны");

    }

    @Test
    void historyManagerSaver() {

        Task taskFirst = new Task("Завтрак", "Позавтракать", Status.DONE);
        Task taskSecond = new Task("Обед", "Пообедать", Status.IN_PROGRESS);
        taskSecond.setId(1);

        List<Task> temp = new ArrayList<>();
        temp.add(taskFirst);

        manager.addTask(taskFirst);
        manager.receivingTaskById(1);
        manager.updateTask(taskSecond);

        Assertions.assertEquals(temp, manager.getHistory(), "Старое значение не сохранилось");
        Assertions.assertFalse(manager.getHistory().isEmpty(),"История пустая");
    }
}