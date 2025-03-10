package test.managers;

import managers.Managers;
import interfaces.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ManagersTest {

    @Test
    void getDefault () {
        TaskManager manager = Managers.getDefault();

        Assertions.assertNotNull(manager);
    }

    @Test
    void getDefaultHistory() {
        HistoryManager manager = Managers.getDefaultHistory();

        Assertions.assertNotNull(manager);
    }

}
