package http;

import com.sun.net.httpserver.HttpExchange;
import interfaces.TaskManager;
import tasks.Task;

import java.io.IOException;

public final class TaskHandler extends SelectHandlerMethod {

    /**
     * Конструктор класса.
     *
     * @param taskManager менеджер задач.
     */
    public TaskHandler(final TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    void getTask(final HttpExchange httpExchange, final String[] path) throws IOException {
        if (path.length == 2) {
            sendText(httpExchange, gson.toJson(taskManager.getTasks()));
        } else {
            sendText(httpExchange, gson.toJson(taskManager.getTaskById(Integer.parseInt(path[2]))));
        }
    }

    @Override
    void postTask(final HttpExchange httpExchange, final String[] path) throws IOException {
        Task task = readerFromJson(httpExchange);

        if (task.getId() == 0) {
            taskManager.addTask(task);
        } else {
            taskManager.updateTask(task);
        }
        sendCreated(httpExchange);
    }

    @Override
    void deleteTask(final HttpExchange httpExchange, final String[] path) throws IOException {
        taskManager.deleteTaskById(Integer.parseInt(path[2]));
        sendText(httpExchange, "Task deleted");
    }
}
