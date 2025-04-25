package http;

import com.google.gson.stream.JsonReader;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import exceptions.ManagerAddTaskException;
import interfaces.TaskManager;
import tasks.Subtask;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

public class SubtaskHandler extends BaseHandler implements HttpHandler {

    TaskManager taskManager;

    public SubtaskHandler(TaskManager taskManager) {
        super(taskManager);
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        URI uri = httpExchange.getRequestURI();
        String path = uri.getPath();
        String[] splitPath = path.split("/");

        try {
            if ("GET".equals(httpExchange.getRequestMethod())) {
                String json;

                if (splitPath.length == 2) {
                    json = gson.toJson(taskManager.getSubtasks());
                } else {
                    json = gson.toJson(taskManager.getSubtaskById(Integer.parseInt(splitPath[2])));
                }

                sendText(httpExchange, json);
            } else if ("POST".equals(httpExchange.getRequestMethod())) {
                Subtask task = SubtaskReaderFromJson(httpExchange);

                if (task.getId() == 0) {
                    taskManager.addSubtask(task);
                } else {
                    taskManager.updateSubtask(task);
                }

                sendSuccessful(httpExchange);
            } else if ("DELETE".equals(httpExchange.getRequestMethod())) {
                taskManager.deleteSubtaskById(Integer.parseInt(splitPath[2]));
                sendText(httpExchange, "Задача удалена");
            }
        } catch (NullPointerException e) {
            sendNotFound(httpExchange);
        } catch (ManagerAddTaskException e) {
            sendHasInteractions(httpExchange);
        } catch (Exception e) {
            sendError(httpExchange, "Internal Server Error" + e.getMessage());
        }
    }

    protected Subtask SubtaskReaderFromJson(HttpExchange httpExchange) throws IOException {
        try (JsonReader reader = new JsonReader(new InputStreamReader(httpExchange.getRequestBody()))) {
            return gson.fromJson(reader, Subtask.class);
        }
    }
}

