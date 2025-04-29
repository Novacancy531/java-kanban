package http;

import com.sun.net.httpserver.HttpExchange;
import enums.Status;
import interfaces.TaskManager;
import tasks.Epic;

import java.io.IOException;
import java.util.ArrayList;

public final class EpicHandler extends SelectHandlerMethod {

    /**
     * Конструктор класса.
     *
     * @param taskManager менеджер задач.
     */
    public EpicHandler(final TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    void getTask(final HttpExchange httpExchange, final String[] path) throws IOException {
        if (path.length == 4) {
            sendText(httpExchange, gson.toJson(taskManager.getSubtasksFromEpic(taskManager.getEpicById(
                    Integer.parseInt(path[2])))));
        } else if (path.length == 3) {
            sendText(httpExchange, gson.toJson(taskManager.getEpicById(Integer.parseInt(path[2]))));
        } else {
            sendText(httpExchange, gson.toJson(taskManager.getEpics()));
        }
    }

    @Override
    void postTask(final HttpExchange httpExchange, final String[] path) throws IOException {
        Epic epic = (Epic) readerFromJson(httpExchange);
        epic.setStatus(Status.NEW);
        epic.setSubtasksList(new ArrayList<>());
        taskManager.addEpic(epic);
        sendCreated(httpExchange);
    }

    @Override
    void deleteTask(final HttpExchange httpExchange, final String[] path) throws IOException {
        taskManager.deleteEpicById(Integer.parseInt(path[2]));
        sendText(httpExchange, "Epic deleted");
    }
}

