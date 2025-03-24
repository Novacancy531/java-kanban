package managers;

import enums.Status;
import interfaces.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;

class FileBackedTaskManagerTest {

    /**
     * Менеджер задач.
     */
    private TaskManager manager;
    /**
     * Задача 1.
     */
    private Task task;
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

    /**
     * Временный файл-хранилище.
     */
    private File tempFile;

    /**
     * Временная директория.
     */
    @TempDir
    private static File testDir;

    @BeforeEach
    void setUp() {
        tempFile = new File(testDir, "storage.csv");
        manager = new FileBackedTaskManager(tempFile);
        task = new Task("1", "Задача 1", Status.NEW);
        epic = new Epic("3", "Большая задача 3");
        subtask1 = new Subtask("4", "Подзадача 4", Status.NEW, 2);
        subtask2 = new Subtask("5", "Подзадача 5", Status.NEW, 2);
    }


    @SuppressWarnings("checkstyle:MagicNumber")
    @Test
    void saveAndLoad() {
        manager.addTask(task);
        manager.addEpic(epic);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);

        TaskManager loaderManager = FileBackedTaskManager.loadFromFile(tempFile);

        Assertions.assertNotNull(tempFile, "Файл не существует.");
        Assertions.assertEquals(1, loaderManager.getTasks().size(), "Задача не загрузилась.");
        Assertions.assertEquals(1, loaderManager.getEpics().size(), "Большая задача не загрузилась.");
        Assertions.assertEquals(2, loaderManager.getSubtasks().size(), "Подзадача не загрузилась.");

        Assertions.assertEquals(task, loaderManager.getTasks().getFirst());
        Assertions.assertEquals(epic, loaderManager.getEpics().getFirst());
        Assertions.assertEquals(subtask1, loaderManager.getSubtasks().getFirst());
        Assertions.assertEquals(subtask2, loaderManager.getSubtasks().get(1));

        manager.addTask(new Task("2", "Задача 2", Status.NEW));
        manager.updateSubtask(new Subtask("4", "Задача 4", Status.DONE, 2, 3));

        TaskManager secondLoaderManager = FileBackedTaskManager.loadFromFile(tempFile);

        Assertions.assertEquals(Status.IN_PROGRESS, secondLoaderManager.getEpics().getFirst().getStatus(),
                "Статус эпика не обновился.");
        Assertions.assertEquals(5, secondLoaderManager.getTasks().get(1).getId(),
                "Неверно присваиваются идентификаторы после загрузки.");
        Assertions.assertEquals(2, secondLoaderManager.getTasks().size(),
                "Задача после загрузки добавилась с ошибкой.");
    }
}
