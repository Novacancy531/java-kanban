package http;

import com.google.gson.stream.JsonReader;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import exceptions.ManagerAddTaskException;
import interfaces.TaskManager;
import tasks.Task;

import java.io.IOException;
import java.io.InputStreamReader;

public final class TaskHandler extends BaseHandler implements HttpHandler {

    /**
     * Конструктор класса.
     *
     * @param taskManager менеджер задач.
     */
    public TaskHandler(final TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(final HttpExchange httpExchange) throws IOException {
        String[] splitPath = splitUriPath(httpExchange);

        try {
            if ("GET".equals(httpExchange.getRequestMethod())) {
                String json;

                if (splitPath.length == 2) {
                    json = gson.toJson(taskManager.getTasks());
                } else {
                    json = gson.toJson(taskManager.getTaskById(Integer.parseInt(splitPath[2])));
                }

                sendText(httpExchange, json);
            } else if ("POST".equals(httpExchange.getRequestMethod())) {
                Task task = taskReaderFromJson(httpExchange);

                if (task.getId() == 0) {
                    taskManager.addTask(task);
                } else {
                    taskManager.updateTask(task);
                }

                sendCreated(httpExchange);
            } else if ("DELETE".equals(httpExchange.getRequestMethod())) {
                taskManager.deleteTaskById(Integer.parseInt(splitPath[2]));
                sendText(httpExchange, "Задача удалена");
            }
        } catch (NullPointerException e) {
            sendNotFound(httpExchange);
        } catch (ManagerAddTaskException e) {
            sendHasInteractions(httpExchange);
        }
    }

    private Task taskReaderFromJson(final HttpExchange httpExchange) throws IOException {
        try (JsonReader reader = new JsonReader(new InputStreamReader(httpExchange.getRequestBody()))) {
            return gson.fromJson(reader, Task.class);
        }
    }
}
