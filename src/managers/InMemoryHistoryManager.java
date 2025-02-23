package managers;

import interfaces.*;
import tasks.*;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final ArrayList<Task> historyList;


    public InMemoryHistoryManager() {
        historyList = new ArrayList<>();
    }

    @Override
    public void add(Task object) {
        if (historyList.size() == 10) {
            historyList.remove(0);
        }
        historyList.add(object);
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(historyList);
    }

}
