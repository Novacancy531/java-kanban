package managers;

import assistants.Initialize;
import interfaces.TaskManager;
import interfaces.HistoryManager;

import java.io.File;


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

    public static TaskManager FileBackedTaskManager(File file) {
        return new FileBackedTaskManager(file);
    }

    /**
     * Метод для создания менеджера истории по умолчанию.
     * @return возвращает менеджер истории.
     */
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
