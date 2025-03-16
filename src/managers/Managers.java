package managers;

import interfaces.TaskManager;
import interfaces.HistoryManager;


public final class Managers {

    private Managers() {
    }

    /**
     * Метод для создания менеджера по умолчанию.
     * @return возвращает менеджер.
     */
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    /**
     * Метод для создания менеджера истории по умолчанию.
     * @return возвращает менеджер истории.
     */
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
