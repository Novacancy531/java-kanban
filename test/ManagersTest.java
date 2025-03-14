import managers.Managers;
import interfaces.TaskManager;
import interfaces.HistoryManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ManagersTest {

    @Test
    void getDefault() {
        TaskManager manager = Managers.getDefault();

        Assertions.assertNotNull(manager);
    }

    @Test
    void getDefaultHistory() {
        HistoryManager manager = Managers.getDefaultHistory();

        Assertions.assertNotNull(manager);
    }

}
