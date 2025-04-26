package http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import exceptions.ManagerAddTaskException;
import interfaces.TaskManager;

import java.io.IOException;

public abstract class SelectHandlerMethod extends BaseHandler implements HttpHandler {

    protected SelectHandlerMethod(final TaskManager manager) {
        super(manager);
    }

    /**
     * Базовый обработчик.
     * @param httpExchange the exchange containing the request from the
     *                 client and used to send the response
     * @throws IOException необходимое исключение.
     */
    @Override
    public void handle(final HttpExchange httpExchange) throws IOException {
        String[] splitPath = splitUriPath(httpExchange);

        try {
            switch (httpExchange.getRequestMethod()) {
                case "GET" -> getTask(httpExchange, splitPath);

                case "POST" -> postTask(httpExchange, splitPath);

                case "DELETE" -> deleteTask(httpExchange, splitPath);


                default -> throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            sendNotFound(httpExchange);
        } catch (ManagerAddTaskException e) {
            sendHasInteractions(httpExchange);
        }
    }

    abstract void getTask(HttpExchange httpExchange, String[] path) throws IOException;

    abstract void postTask(HttpExchange httpExchange, String[] path) throws IOException;

    abstract void deleteTask(HttpExchange httpExchange, String[] path) throws IOException;
}
