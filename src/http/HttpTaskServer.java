package http;

import assistants.Initialize;
import com.sun.net.httpserver.HttpServer;
import enums.Status;
import interfaces.TaskManager;
import managers.Managers;
import tasks.Task;

import java.io.*;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;

public class HttpTaskServer {
    private static final TaskManager taskManager = Managers.FileBackedTaskManager(Initialize.initializeStorage());


    public static void main(String[] args) {
        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);

            httpServer.createContext("/tasks", new TaskHandler(taskManager));
            httpServer.createContext("/subtasks", new SubtaskHandler(taskManager));
        /*httpServer.createContext("/epics", new EpicHandler(taskManager));
        httpServer.createContext("/history", new HistoryHandler(taskManager));
        httpServer.createContext("/prioritized", new PrioritizedHandler(taskManager));*/

            httpServer.start();
            System.out.println("Server started on port 8080");
        } catch (IOException e) {
            System.out.println("Server failed to start");
            e.printStackTrace();
        }
    }


}


