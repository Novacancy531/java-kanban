package http;

import com.sun.net.httpserver.HttpExchange;
import interfaces.TaskManager;
import tasks.Subtask;

import java.io.IOException;

public final class SubtaskHandler extends SelectHandlerMethod {

    /**
     * Конструктор класса.
     *
     * @param taskManager менеджер задач.
     */
    public SubtaskHandler(final TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    void getTask(final HttpExchange httpExchange, final String[] path) throws IOException {
        if (path.length == 2) {
            sendText(httpExchange, gson.toJson(taskManager.getSubtasks()));
        } else {
            sendText(httpExchange, gson.toJson(taskManager.getSubtaskById(Integer.parseInt(path[2]))));
        }
    }

    @Override
    void postTask(final HttpExchange httpExchange, final String[] path) throws IOException {
        Subtask subtask = (Subtask) readerFromJson(httpExchange);

        if (taskManager.getEpicById(subtask.getEpicId()) != null) {
            if (subtask.getId() == 0) {
                taskManager.addSubtask(subtask);
            } else {
                taskManager.updateSubtask(subtask);
            }
            sendCreated(httpExchange);
        }

        sendNotFound(httpExchange);
    }

    @Override
    void deleteTask(final HttpExchange httpExchange, final String[] path) throws IOException {
        taskManager.deleteSubtaskById(Integer.parseInt(path[2]));
        sendText(httpExchange, "Subtask deleted");
    }
}

