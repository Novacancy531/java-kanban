package http;

import com.sun.net.httpserver.HttpExchange;
import interfaces.TaskManager;

import java.io.IOException;

public final class PrioritizedHandler extends SelectHandlerMethod {

    /**
     * Конструктор класса.
     *
     * @param taskManager менеджер задач.
     */
    public PrioritizedHandler(final TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    void getTask(final HttpExchange httpExchange, final String[] path) throws IOException {
        sendText(httpExchange, gson.toJson(taskManager.getPrioritizedTasks()));
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
