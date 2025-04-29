package http;

import com.sun.net.httpserver.HttpExchange;
import interfaces.TaskManager;

import java.io.IOException;

public final class HistoryHandler extends SelectHandlerMethod {

    /**
     * Конструктор класса.
     *
     * @param taskManager менеджер задач.
     */
    public HistoryHandler(final TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    void getTask(final HttpExchange httpExchange, final String[] path) throws IOException {
        sendText(httpExchange, gson.toJson(taskManager.getHistory()));
    }

    @Override
    void postTask(final HttpExchange httpExchange, final String[] path) throws IOException {
        sendMethodNotAllowed(httpExchange);
    }

    @Override
    void deleteTask(final HttpExchange httpExchange, final String[] path) throws IOException {
        sendMethodNotAllowed(httpExchange);
    }
}
