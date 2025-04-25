package http;

import assistants.Initialize;
import com.sun.net.httpserver.HttpServer;
import interfaces.TaskManager;
import managers.Managers;


import java.io.IOException;
import java.net.InetSocketAddress;


@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public class HttpTaskServer {


    /**
     * Основной метод работы программы.
     * @param args аргументы запуска.
     */
    public static void main(final String[] args) {
        start();
    }

    private static void start() {
        final TaskManager taskManager = Managers.fileBackedTaskManager(Initialize.initializeStorage());


        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);

            httpServer.createContext("/tasks", new TaskHandler(taskManager));
            httpServer.createContext("/subtasks", new SubtaskHandler(taskManager));
            httpServer.createContext("/epics", new EpicHandler(taskManager));
            httpServer.createContext("/history", new HistoryHandler(taskManager));
            httpServer.createContext("/prioritized", new PrioritizedHandler(taskManager));

            httpServer.start();
            System.out.println("Server started on port 8080");
        } catch (IOException e) {
            System.out.println("Server failed to start");
        }
    }

    private static void stop(final HttpServer httpServer) {
        httpServer.stop(10);
    }


}


