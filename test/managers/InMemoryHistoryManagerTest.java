package managers;

import enums.Status;
import interfaces.HistoryManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


class InMemoryHistoryManagerTest {

    /**
     * Поле менеджера истории.
     */
    private HistoryManager historyManager;
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
    void setUp() {
        int id = 1;
        historyManager = new InMemoryHistoryManager();
        task1 = new Task("1", "Задача 1", Status.NEW, Duration.ZERO, LocalDateTime.now());
        task1.setId(id++);
        task2 = new Task("2", "Задача 2", Status.NEW, Duration.ZERO, LocalDateTime.now());
        task2.setId(id++);
        epic = new Epic("3", "Большая задача 3");
        epic.setId(id++);
        subtask1 = new Subtask("4", "Задача 4", Status.NEW, epic.getId(), Duration.ZERO,
                LocalDateTime.now());
        subtask1.setId(id++);
        subtask2 = new Subtask("5", "Задача 5", Status.NEW, epic.getId(), Duration.ZERO,
                LocalDateTime.now());
        subtask2.setId(id);
    }


    @Test
    void add() {
        final int historySize = 3;
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(epic);

        Assertions.assertNotNull(historyManager.getHistory());
        Assertions.assertEquals(task1, historyManager.getHistory().get(0),
                "Задача 1 не попала в историю.");
        Assertions.assertEquals(task2, historyManager.getHistory().get(1),
                "Задача 2 не попала  в историю.");
        Assertions.assertEquals(epic, historyManager.getHistory().get(2),
                "Большая задача 1 не попала в историю.");

        historyManager.add(task2);

        Assertions.assertEquals(historySize, historyManager.getHistory().size(),
                "Размер истории после замены изменился.");
        Assertions.assertEquals(task2, historyManager.getHistory().get(2),
                "Задача 1 не на своем месте.");
        Assertions.assertEquals(epic, historyManager.getHistory().get(1),
                "Задача 2 не на своем месте.");
        Assertions.assertEquals(task1, historyManager.getHistory().get(0),
                "Большая задача 1 не на своем месте.");
    }

    @Test
    void remove() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.remove(task1.getId());
        Assertions.assertEquals(1, historyManager.getHistory().size(),
                "Размер истории после удаления не изменился.");
        Assertions.assertEquals(task2, historyManager.getHistory().getFirst(),
                "Ожидалась другая задача.");

        historyManager.remove(task2.getId());
        Assertions.assertEquals(0, historyManager.getHistory().size());

    }

    @Test
    void getHistory() {
        Assertions.assertEquals(0, historyManager.getHistory().size());

        historyManager.add(epic);
        historyManager.add(subtask1);
        historyManager.add(subtask2);

        List<Task> reference = new ArrayList<>();
        reference.add(epic);
        reference.add(subtask1);
        reference.add(subtask2);

        Assertions.assertNotNull(historyManager.getHistory(),
                "История пустая.");
        Assertions.assertEquals(reference, historyManager.getHistory(),
                "Неправильный вывод истории.");
    }

    @Test
    void doubleTasksInHistory() {
        Assertions.assertEquals(0, historyManager.getHistory().size());
        historyManager.add(task1);
        Assertions.assertEquals(1, historyManager.getHistory().size());
        historyManager.add(task1);
        Assertions.assertEquals(1, historyManager.getHistory().size());
    }
}
