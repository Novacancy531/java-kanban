package http;

import com.google.gson.stream.JsonReader;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import enums.Status;
import exceptions.ManagerAddTaskException;
import interfaces.TaskManager;
import tasks.Epic;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public final class EpicHandler extends BaseHandler implements HttpHandler {

    /**
     * Конструктор класса.
     *
     * @param taskManager менеджер задач.
     */
    public EpicHandler(final TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(final HttpExchange httpExchange) throws IOException {
        String[] splitPath = splitUriPath(httpExchange);

        try {
            if ("GET".equals(httpExchange.getRequestMethod())) {
                String json;

                if (splitPath.length == 4) {
                    Epic epic = taskManager.getEpicById(Integer.parseInt(splitPath[2]));
                    json = gson.toJson(taskManager.getSubtasksFromEpic(epic));
                } else if (splitPath.length == 3) {
                    json = gson.toJson(taskManager.getEpicById(Integer.parseInt(splitPath[2])));
                } else {
                    json = gson.toJson(taskManager.getEpics());
                }

                sendText(httpExchange, json);
            } else if ("POST".equals(httpExchange.getRequestMethod())) {
                Epic epic = epicReaderFromJson(httpExchange);
                epic.setStatus(Status.NEW);
                epic.setSubtasksList(new ArrayList<>());
                taskManager.addEpic(epic);
                sendCreated(httpExchange);
            } else if ("DELETE".equals(httpExchange.getRequestMethod())) {
                taskManager.deleteEpicById(Integer.parseInt(splitPath[2]));
                sendText(httpExchange, "Задача удалена");
            }
        } catch (NullPointerException e) {
            sendNotFound(httpExchange);
        } catch (ManagerAddTaskException e) {
            sendHasInteractions(httpExchange);
        }
    }

    private Epic epicReaderFromJson(final HttpExchange httpExchange) throws IOException {
        try (JsonReader reader = new JsonReader(new InputStreamReader(httpExchange.getRequestBody()))) {
            return gson.fromJson(reader, Epic.class);
        }
    }
}

