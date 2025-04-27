package http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import enums.Status;
import interfaces.TaskManager;
import managers.FileBackedTaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HttpTest {

    /**
     * Экземпляр Gson.
     */
    private Gson gson;
    /**
     * Менеджер задач.
     */
    private TaskManager manager;
    /**
     * Задача 1.
     */
    private Task task;
    /**
     * Задача 2.
     */
    private Task task2;
    /**
     * Задача 3.
     */
    private Task task3;
    /**
     * Большая задача 1.
     */
    private Epic epic;
    /**
     * Подзадача 1.
     */
    private Subtask subtask1;
    /**
     * Подзадача 2.
     */
    private Subtask subtask2;

    /**
     * Временный файл-хранилище.
     */
    private File tempFile;

    /**
     * Временная директория.
     */
    @TempDir
    private static File testDir;

    @BeforeEach
    void setUp() {
        tempFile = new File(testDir, "storage.csv");
        manager = new FileBackedTaskManager(tempFile);
        gson = new GsonBuilder()
                .registerTypeAdapter(Duration.class, new TypoAdapters.DurationTypeAdapter())
                .registerTypeAdapter(LocalDateTime.class, new TypoAdapters.LocalDateAdapter())
                .serializeNulls()
                .setPrettyPrinting()
                .create();
        task = new Task("1", "Задача 1", Status.NEW, Duration.ofMinutes(5), LocalDateTime.now());
        task2 = new Task("2", "Задача 3", Status.NEW, Duration.ofMinutes(5), LocalDateTime.now());
        task3 = new Task("3", "Задача 1", Status.NEW, Duration.ofMinutes(5), LocalDateTime.now());
        epic = new Epic("3", "Большая задача 3");
        subtask1 = new Subtask("4", "d4", Status.NEW, 1, Duration.ZERO, LocalDateTime.now());
        subtask2 = new Subtask("5", "d5", Status.NEW, 1, Duration.ZERO, LocalDateTime.now());
        manager.removeAllEpics();
        manager.removeAllSubtasks();
        manager.removeAllTasks();
        HttpTaskServer.start(manager);
    }

    @AfterEach
    void serverStop() {
        HttpTaskServer.stop();
    }

    @Test
    void getTasks() throws IOException, InterruptedException {
        manager.addTask(task2);

        HttpResponse<String> response;
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/1"))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Task requestTask = gson.fromJson(response.body(), Task.class);

            assertEquals(HttpURLConnection.HTTP_OK, response.statusCode(), "Неверный статус-код ответа");

            assertNotNull(response.body(), "Тело ответа не получено");
            assertEquals(task2.getName(), requestTask.getName(), "Имя задачи не сходится.");
            assertEquals(task2, requestTask, "Задача не сходится.");
        }
    }

    @Test
    void addAndUpdateTask() throws IOException, InterruptedException {
        String taskJson = gson.toJson(task);

        HttpResponse<String> response;
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());


            // Проверяем статус-код
            assertEquals(HttpURLConnection.HTTP_CREATED, response.statusCode(), "Неверный статус-код ответа");

            // Проверяем наличие тела ответа
            assertNotNull(response.body(), "Тело ответа не получено");

            // Проверяем, что создалась одна задача с корректным именем
            List<Task> tasksFromManager = manager.getTasks();

            assertNotNull(tasksFromManager, "Задачи не возвращаются");
            assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
            assertEquals("1", tasksFromManager.getFirst().getName(), "Некорректное имя задачи");
            assertEquals(Status.NEW, tasksFromManager.getFirst().getStatus(), "Некорректный статус задачи.");

            task.setStatus(Status.DONE);
            task.setId(1);
            String updateTaskJson = gson.toJson(task);

            HttpRequest updateRequest = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(updateTaskJson))
                    .build();

            response = client.send(updateRequest, HttpResponse.BodyHandlers.ofString());

            // Проверяем статус-код
            assertEquals(HttpURLConnection.HTTP_CREATED, response.statusCode(), "Неверный статус-код ответа");

            // Проверяем наличие тела ответа
            assertNotNull(response.body(), "Тело ответа не получено");

            // Проверяем, что создалась одна задача с корректным именем
            tasksFromManager = manager.getTasks();

            assertNotNull(tasksFromManager, "Задачи не возвращаются");
            assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
            assertEquals("1", tasksFromManager.getFirst().getName(), "Некорректное имя задачи");
            assertEquals(Status.DONE, tasksFromManager.getFirst().getStatus(), "Некорректный статус");
        }
    }

    @Test
    void deleteTask() throws IOException, InterruptedException {
        manager.addTask(task);

        Assertions.assertEquals(1, manager.getTasks().size(), "Задано неверное количество задач.");

        HttpResponse<String> response;
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/tasks/1"))
                    .header("Content-Type", "application/json")
                    .DELETE()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());


            assertEquals(HttpURLConnection.HTTP_OK, response.statusCode(), "Неверный статус-код ответа");
            assertNotNull(response.body(), "Тело ответа не получено");

            List<Task> tasksFromManager = manager.getTasks();

            assertNotNull(tasksFromManager, "Задачи не возвращаются");
            assertEquals(0, tasksFromManager.size(), "Некорректное количество задач");
        }
    }

    @Test
    void addAndUpdateSubtask() {
    }

    @Test
    void getSubtasks() {

    }

    @Test
    void deleteSubtask() throws IOException, InterruptedException {
        manager.addEpic(epic);
        manager.addSubtask(subtask1);

        Assertions.assertEquals(1, manager.getSubtasks().size(), "Задано неверное количество подзадач.");

        HttpResponse<String> response;
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/subtasks/2"))
                    .header("Content-Type", "application/json")
                    .DELETE()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());


            assertEquals(HttpURLConnection.HTTP_OK, response.statusCode(), "Неверный статус-код ответа");
            assertNotNull(response.body(), "Тело ответа не получено");

            List<Task> tasksFromManager = manager.getSubtasks();

            assertNotNull(tasksFromManager, "Задачи не возвращаются");
            assertEquals(0, tasksFromManager.size(), "Некорректное количество подзадач");
        }
    }

    @Test
    void addEpic() {
    }

    @Test
    void getEpics() {

    }

    @Test
    void deleteEpic() throws IOException, InterruptedException {
        manager.addEpic(epic);
        manager.addSubtask(subtask1);

        Assertions.assertEquals(1, manager.getEpics().size(), "Задано неверное количество эпиков.");

        HttpResponse<String> response;
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/epics/1"))
                    .header("Content-Type", "application/json")
                    .DELETE()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());


            assertEquals(HttpURLConnection.HTTP_OK, response.statusCode(), "Неверный статус-код ответа");
            assertNotNull(response.body(), "Тело ответа не получено");

            List<Task> epicsFromManager = manager.getEpics();

            assertNotNull(epicsFromManager, "Задачи не возвращаются");
            assertEquals(0, epicsFromManager.size(), "Некорректное количество подзадач");
        }
    }

    @Test
    void getSubtasksList() {

    }

    @Test
    void getHistory() throws IOException, InterruptedException {
        task.setStartTime(LocalDateTime.of(2020, 10, 10, 9, 10));
        task2.setStartTime(LocalDateTime.of(2020, 10, 10, 10, 10));
        task3.setStartTime(LocalDateTime.of(2020, 10, 10, 11, 10));
        manager.addTask(task);
        manager.addTask(task2);
        manager.addTask(task3);

        manager.getTaskById(2);
        manager.getTaskById(3);
        manager.getTaskById(1);

        HttpResponse<String> response;
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/history"))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            List<Task> tasks = gson.fromJson(response.body(), new TypeToken<List<Task>>() {
            }.getType());


            // Проверяем статус-код
            assertEquals(HttpURLConnection.HTTP_OK, response.statusCode(), "Неверный статус-код ответа");

            // Проверяем наличие тела ответа
            assertNotNull(response.body(), "Тело ответа не получено");

            assertNotNull(tasks, "Задачи не возвращаются");
            assertEquals(3, tasks.size(), "Некорректное количество задач");
            assertEquals(manager.getHistory(), tasks, "Списки не сходятся.");


        }
    }

    @Test
    void getPriorityList() throws IOException, InterruptedException {

        task.setStartTime(LocalDateTime.of(2020, 10, 10, 9, 10));
        task2.setStartTime(LocalDateTime.of(2020, 10, 10, 10, 10));
        task3.setStartTime(LocalDateTime.of(2020, 10, 10, 11, 10));
        manager.addTask(task);
        manager.addTask(task2);
        manager.addTask(task3);


        HttpResponse<String> response;
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/prioritized"))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Set<Task> tasks = gson.fromJson(response.body(), new TypeToken<Set<Task>>() {
            }.getType());


            // Проверяем статус-код
            assertEquals(HttpURLConnection.HTTP_OK, response.statusCode(), "Неверный статус-код ответа");

            // Проверяем наличие тела ответа
            assertNotNull(response.body(), "Тело ответа не получено");

            assertNotNull(tasks, "Задачи не возвращаются");
            assertEquals(manager.getPrioritizedTasks(), tasks, "Списки не сходятся.");


        }
    }


}
