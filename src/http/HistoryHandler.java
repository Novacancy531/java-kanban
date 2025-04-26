package http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import interfaces.TaskManager;

import java.io.IOException;

public final class HistoryHandler extends SelectHandlerMethod implements HttpHandler {

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
        sendNotFound(httpExchange);
    }

    @Override
    void deleteTask(final HttpExchange httpExchange, final String[] path) throws IOException {
        sendNotFound(httpExchange);
    }
}
