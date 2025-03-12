package test.managers;

import enums.Status;
import interfaces.HistoryManager;
import interfaces.TaskManager;
import managers.InMemoryHistoryManager;
import managers.InMemoryTaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.LinkedList;
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
    public void setUp() {
        int id = 1;
        historyManager = new InMemoryHistoryManager();
        task1 = new Task("1", "Задача 1", Status.NEW);
        task1.setId(id++);
        task2 = new Task("2", "Задача 2", Status.NEW);
        task2.setId(id++);
        epic = new Epic("3", "Большая задача 3");
        epic.setId(id++);
        subtask1 = new Subtask("4", "Задача 4", Status.NEW, epic.getId());
        subtask1.setId(id++);
        subtask2 = new Subtask("5", "Задача 5", Status.NEW, epic.getId());
        subtask2.setId(id);
    }


    @Test
    void add() {
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

        Assertions.assertEquals(3, historyManager.getHistory().size(),
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
    }

    @Test
    void getHistory() {
    }

    @Test
    void addLinkLast() {
    }

    @Test
    void removeNode() {
    }
}
