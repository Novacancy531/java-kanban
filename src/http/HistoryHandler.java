package http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import interfaces.TaskManager;

import java.io.IOException;

public final class HistoryHandler extends BaseHandler implements HttpHandler {

    /**
     * Конструктор класса.
     * @param taskManager менеджер задач.
     */
   public HistoryHandler(final TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(final HttpExchange httpExchange) throws IOException {
            if ("GET".equals(httpExchange.getRequestMethod())) {
                String json = gson.toJson(taskManager.getHistory());
                sendText(httpExchange, json);
            }
    }
}
