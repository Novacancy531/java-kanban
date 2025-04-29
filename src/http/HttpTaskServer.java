package http;

import assistants.Initialize;
import com.sun.net.httpserver.HttpServer;
import interfaces.TaskManager;
import managers.Managers;

import java.io.IOException;
import java.net.InetSocketAddress;


public final class HttpTaskServer {

    private HttpTaskServer() {
    }

    /**
     * Порт для сервера.
     */
    private static final int PORT = 8080;
    /**
     * Экземпляр Http сервера.
     */
    private static HttpServer httpServer;

    /**
     * Основной метод работы программы.
     *
     * @param args аргументы запуска.
     */
    public static void main(final String[] args) {
        start(Managers.fileBackedTaskManager(Initialize.initializeStorage()));
    }

    /**
     * Метод для запуска сервера.
     *
     * @param taskManager экземпляр менеджера задач для сервера.
     */
    public static void start(final TaskManager taskManager) {

        try {
            httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);

            httpServer.createContext("/tasks", new TaskHandler(taskManager));
            httpServer.createContext("/subtasks", new SubtaskHandler(taskManager));
            httpServer.createContext("/epics", new EpicHandler(taskManager));
            httpServer.createContext("/history", new HistoryHandler(taskManager));
            httpServer.createContext("/prioritized", new PrioritizedHandler(taskManager));

            httpServer.start();
            System.out.println("Server started on port " + PORT);
        } catch (IOException e) {
            System.out.println("Server failed to start");
        }
    }

    /**
     * Метод для остановки сервера.
     */
    public static void stop() {
        httpServer.stop(2);
    }
}
