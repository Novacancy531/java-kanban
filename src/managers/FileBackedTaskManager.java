package managers;

import assistants.TaskStringConverter;
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
                writer.write(TaskStringConverter.taskToString(task) + "\n");
            }
            for (Task epic : getEpics()) {
                writer.write(TaskStringConverter.taskToString(epic) + "\n");
            }
            for (Task subtask : getSubtasks()) {
                writer.write(TaskStringConverter.taskToString(subtask) + "\n");
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
                var task = TaskStringConverter.taskFromString(reader.readLine());

                switch (task.getType()) {
                    case TaskType.EPIC -> manager.epics.put(task.getId(), (Epic) task);

                    case SUBTASK -> {
                        manager.subtasks.put(task.getId(), (Subtask) task);
                        Epic epic = manager.epics.get(((Subtask) task).getEpicId());
                        epic.getSubtasksList().add(task.getId());
                    }

                    default -> manager.tasks.put(task.getId(), task);
                }

                if (task.getId() > manager.numberOfTasks) {
                    manager.numberOfTasks = task.getId();
                }
            }

            return manager;

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка загрузки данных.");
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
