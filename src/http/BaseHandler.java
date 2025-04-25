package http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.sun.net.httpserver.HttpExchange;
import interfaces.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

class BaseHandler {

    protected final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Duration.class, new TypoAdapters.DurationTypeAdapter())
            .registerTypeAdapter(LocalDateTime.class, new TypoAdapters.LocalDateAdapter())
            .serializeNulls()
            .setPrettyPrinting()
            .create();
    protected final TaskManager manager;

    protected BaseHandler(TaskManager manager) {
        this.manager = manager;
    }

    protected void sendText(HttpExchange exchange, String text) throws IOException {
        byte[] response = text.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.sendResponseHeaders(200, response.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response);
        }

        exchange.close();
    }

    protected void sendSuccessful(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(201, 0);
        exchange.close();
    }

    protected void sendNotFound(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(404, 0);
        exchange.close();
    }

    protected void sendHasInteractions(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(406, 0);
        exchange.close();
    }

    protected void sendError(HttpExchange h, String message) throws IOException {
        h.sendResponseHeaders(500, 0);
        h.getResponseBody().write(message.getBytes());
        h.close();
    }

}
