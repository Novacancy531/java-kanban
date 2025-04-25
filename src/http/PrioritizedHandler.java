package http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import interfaces.TaskManager;

import java.io.IOException;

public final class PrioritizedHandler extends BaseHandler implements HttpHandler {

    /**
     * Конструктор класса.
     *
     * @param taskManager менеджер задач.
     */
    public PrioritizedHandler(final TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(final HttpExchange httpExchange) throws IOException {
        if ("GET".equals(httpExchange.getRequestMethod())) {
            String json = gson.toJson(taskManager.getPrioritizedTasks());
            sendText(httpExchange, json);
            System.out.println(taskManager.getPrioritizedTasks());
        }
    }
}
