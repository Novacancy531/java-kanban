package managers;

import interfaces.HistoryManager;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class InMemoryHistoryManager implements HistoryManager {

    /**
     * Карта для хранения id задач и Node задач.
     */
    private final HashMap<Integer, Node<Task>> historyList;
    /**
     * Первый Node (Головной).
     */
    private Node<Task> head;
    /**
     * Последний Node (Замыкающий).
     */
    private Node<Task> tail;

    /**
     * Конструктор класса.
     */
    public InMemoryHistoryManager() {
        historyList = new HashMap<>();
        this.head = null;
        this.tail = null;
    }

    /**
     * Метод проверяет есть ли задача в historyList и заменяет ее
     * или добавляет при ее отсутствии ранее.
     *
     * @param task задача из менеджера задач.
     */
    @Override
    public void add(final Task task) {
        if (historyList.containsKey(task.getId())) {
            remove(task.getId());
        }

        addLinkLast(task);
        historyList.put(task.getId(), tail);
    }

    @Override
    public void remove(final int id) {
        removeNode(historyList.get(id));
        historyList.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        ArrayList<Task> tempList = new ArrayList<>();

        Node<Task> current = head;
        while (current != null) {
            tempList.add(current.data);
            current = current.next;
        }

        return tempList;
    }

    /**
     * Добавляет Node и привязывает его последним.
     * Устанавливает Node "хвостом".
     *
     * @param task задача из менеджера задач.
     */
    public void addLinkLast(final Task task) {
        Node<Task> newNode = new Node<>(task);

        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
    }

    /**
     * Удаляет Node и связывает предыдущий и следующий Node между собой.
     *
     * @param node Node который требуется удалить.
     */
    public void removeNode(final Node<Task> node) {

        if (node.prev == null) {
            head = node.next;
            head.prev = null;
        } else if (node.next == null) {
            tail = node.prev;
            tail.next = null;
        } else {
            Node<Task> prev = node.prev;
            Node<Task> next = node.next;

            prev.next = node.next;
            next.prev = node.prev;
        }
    }

    public static class Node<T> {
        /**
         * Поле для хранения задач.
         */
        private final T data;
        /**
         * Поле для хранения ссылки на предыдущий Node.
         */
        private Node<T> next;
        /**
         * Поле для хранения ссылки на следующий Node.
         */
        private Node<T> prev;

        Node(final T task) {
            this.data = task;
            this.next = null;
            this.prev = null;
        }
    }
}
