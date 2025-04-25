package http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import interfaces.TaskManager;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

class BaseHandler {

    /**
     * Экземпляр JSON.
     */
    protected final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Duration.class, new TypoAdapters.DurationTypeAdapter())
            .registerTypeAdapter(LocalDateTime.class, new TypoAdapters.LocalDateAdapter())
            .serializeNulls()
            .setPrettyPrinting()
            .create();
    /**
     * Экземпляр менеджера задач.
     */
    protected final TaskManager taskManager;

    protected BaseHandler(final TaskManager manager) {
        this.taskManager = manager;
    }

    protected void sendText(final HttpExchange exchange, final String text) throws IOException {
        byte[] response = text.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response);
        }

        exchange.close();
    }

    protected void sendCreated(final HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_CREATED, 0);
        exchange.close();
    }

    protected void sendNotFound(final HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
        exchange.close();
    }

    protected void sendHasInteractions(final HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_ACCEPTABLE, 0);
        exchange.close();
    }

    protected String[] splitUriPath(final HttpExchange httpExchange) {
        URI uri = httpExchange.getRequestURI();
        String path = uri.getPath();
        return path.split("/");
    }

}
