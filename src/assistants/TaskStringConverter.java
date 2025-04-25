package assistants;

import enums.Status;
import enums.TaskType;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

public final class TaskStringConverter {

    private TaskStringConverter() {
    }

    /**
     * Метод создания строки из задачи (объекта).
     * @param task задача.
     * @return задача в виде строки.
     */
    public static String taskToString(final Task task) {

        StringJoiner line = new StringJoiner(",");
        line.add(task.getType().toString())
                .add(task.getName())
                .add(task.getDescription())
                .add(task.getStatus().toString())
                .add(String.valueOf(task.getId()));

        String duration = Optional.ofNullable(task.getDuration())
                .map(Duration::toString)
                .orElse("null");
        line.add(duration);

        String startTimeString = Optional.ofNullable(task.getStartTime())
                .map(LocalDateTime::toString)
                .orElse("null");
        line.add(startTimeString);

        if (task.getType().equals(TaskType.SUBTASK)) {
            line.add(String.valueOf(((Subtask) task).getEpicId()));
            line.add(null);
        } else if (task.getType().equals(TaskType.EPIC)) {
            String endTimeString = Optional.ofNullable(task.getEndTime())
                    .map(LocalDateTime::toString)
                    .orElse("null");
            line.add(null);
            line.add(endTimeString);
        } else {
            line.add(null);
            line.add(null);
        }

        return line.toString();
    }

    /**
     * Метод создания задачи (объекта) из строки.
     * @param line задача в виде строки.
     * @return задача.
     */
    public static Task taskFromString(final String line) {

        List<String> partTask = Arrays.stream(line.split(",")).toList();

        final TaskType type = TaskType.valueOf(partTask.get(0));
        final String name = partTask.get(1);
        final String description = partTask.get(2);
        final Status status = Status.valueOf(partTask.get(3));
        final int id = Integer.parseInt(partTask.get(4));
        final Duration duration = Duration.parse(partTask.get(5));
        final LocalDateTime startTime = LocalDateTime.parse(partTask.get(6));
        final String epicId = partTask.get(7);
        final String endEpicTime = partTask.get(8);


        return switch (type) {
            case TaskType.TASK -> new Task(name, description, status, id, duration, startTime);

            case TaskType.SUBTASK -> new Subtask(name, description, status, Integer.parseInt(epicId),
                    id, duration, startTime);

            case TaskType.EPIC -> {
                Epic epic = new Epic(name, description, id);
                epic.setStatus(status);
                epic.setDuration(duration);
                epic.setStartTime(startTime);
                epic.setEndTime(LocalDateTime.parse(endEpicTime));
                yield epic;
            }

        };

    }
}
