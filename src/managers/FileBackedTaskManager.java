package managers;

import enums.Status;
import enums.TaskType;
import exceptions.ManagerSaveException;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class FileBackedTaskManager extends InMemoryTaskManager {

    /**
     * Файл с расширением .csv предназначен для хранения задач которые разделены на строки.
     */
    private final File dataSave;
    /**
     * Позиция типа задачи в массиве строк.
     */
    private static final int TYPE = 0;
    /**
     * Позиция имени задачи в массиве строк.
     */
    private static final int NAME = 1;
    /**
     * Позиция описания задачи в массиве строк.
     */
    private static final int DESCRIPTION = 2;
    /**
     * Позиция статуса задачи в массиве строк.
     */
    private static final int STATUS = 3;
    /**
     * Позиция идентификатора задачи в массиве строк.
     */
    private static final int ID = 4;
    /**
     * Позиция идентификатора большой задачи в массиве строк.
     */
    private static final int EPIC_ID = 5;
    /**
     * Размер массива для хранения данных задачи.
     */
    private static final int STRING_LENGTH = 6;


    /**
     * Конструктор по умолчанию.
     *
     * @param data файл с задачами.
     */
    public FileBackedTaskManager(final File data) {
        dataSave = data;
    }

    /**
     * Метод предназначен для сохранения задач в файл .csv для последующего восстановления задач после
     * перезапуска программы.
     *
     * @throws ManagerSaveException выбрасывается при ошибке сохранения данных.
     */
    public void save() throws ManagerSaveException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataSave, StandardCharsets.UTF_8))) {
            for (Task task : getTasks()) {
                writer.write(taskToString(task) + "\n");
            }
            for (Task epic : getEpics()) {
                writer.write(taskToString(epic) + "\n");
            }
            for (Task subtask : getSubtasks()) {
                writer.write(taskToString(subtask) + "\n");
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка сохранения данных.");
        }
    }

    /**
     * Метод предназначен для восстановления сохраненных задач из файла после запуска программы.
     *
     * @param data файл .csv с задачами.
     * @return экземпляр менеджера после восстановления.
     */
    public static FileBackedTaskManager loadFromFile(final File data) {
        try (BufferedReader reader = new BufferedReader(new FileReader(data, StandardCharsets.UTF_8))) {
            FileBackedTaskManager manager = new FileBackedTaskManager(data);

            while (reader.ready()) {
                var task = manager.taskFromString(reader.readLine());

                if (task.getClass() == Task.class) {
                    manager.getTaskMap().put(task.getId(), task);
                } else if (task.getClass() == Epic.class) {
                    manager.getEpicMap().put(task.getId(), (Epic) task);
                } else if (task.getClass() == Subtask.class) {
                    manager.getSubtaskMap().put(task.getId(), (Subtask) task);
                    Epic epic = manager.getEpicMap().get(((Subtask) task).getEpicId());
                    epic.getSubtasksList().add(task.getId());
                }
            }
            return manager;

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка загрузки данных.");
        }
    }

    private String taskToString(final Task task) {
        String[] taskData = new String[STRING_LENGTH];

        if (task.getClass().equals(Task.class)) {
            taskData[TYPE] = TaskType.TASK.toString();
        } else if (task.getClass().equals(Subtask.class)) {
            taskData[TYPE] = TaskType.SUBTASK.toString();
            taskData[EPIC_ID] = Integer.toString(((Subtask) task).getEpicId());
        } else if ((task.getClass().equals(Epic.class))) {
            taskData[TYPE] = TaskType.EPIC.toString();
        }

        taskData[NAME] = task.getName();
        taskData[DESCRIPTION] = task.getDescription();
        taskData[STATUS] = task.getStatus().toString();
        taskData[ID] = Integer.toString(task.getId());

        return String.join(",", taskData);
    }

    private Task taskFromString(final String line) {
        String[] taskData = line.split(",", STRING_LENGTH);


        switch (TaskType.valueOf(taskData[TYPE])) {
            case TaskType.SUBTASK:
                Subtask subtask = new Subtask(taskData[NAME], taskData[DESCRIPTION], Status.valueOf(taskData[STATUS]),
                        Integer.parseInt(taskData[EPIC_ID]));
                subtask.setId(Integer.parseInt(taskData[ID]));
                return subtask;

            case TaskType.EPIC:
                Epic epic = new Epic(taskData[NAME], taskData[DESCRIPTION]);
                epic.setId(Integer.parseInt(taskData[ID]));
                return epic;

            default:
                Task task = new Task(taskData[NAME], taskData[DESCRIPTION], Status.valueOf(taskData[STATUS]));
                task.setId(Integer.parseInt(taskData[ID]));
                return task;
        }
    }

    @Override
    public void addTask(final Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addSubtask(final Subtask subtask) {
        super.addSubtask(subtask);
        save();
    }

    @Override
    public void addEpic(final Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeAllSubtasks() {
        super.removeAllSubtasks();
        save();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    @Override
    public void updateTask(final Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubtask(final Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void updateEpic(final Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void deleteTaskById(final int id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteSubtaskById(final int id) {
        super.deleteSubtaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(final int id) {
        super.deleteEpicById(id);
        save();
    }
}
