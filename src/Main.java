public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        Task taskFirst = new Task("Завтрак", "Позавтракать", Status.DONE);
        Task taskSecond = new Task("Обед", "Пообедать", Status.IN_PROGRESS);
        Epic epicFirst = new Epic("Ужин", "Поужинать");
        Subtask subtaskFirst = new Subtask("Есть", "Есть гречку", Status.DONE, 3);
        Subtask subtaskSecond = new Subtask("Пить", "Пить компот", Status.IN_PROGRESS, 3);
        Epic epicSecond = new Epic("Полдник", "Полдничать");
        Subtask subtaskThird = new Subtask("Есть", "Есть печенье", Status.IN_PROGRESS, 6);

        TaskManager taskManager = new TaskManager();

        taskManager.addTask(taskFirst);
        taskManager.addTask(taskSecond);
        taskManager.addEpic(epicFirst);
        taskManager.addSubtask(subtaskFirst);
        taskManager.addSubtask(subtaskSecond);
        taskManager.addEpic(epicSecond);
        taskManager.addSubtask(subtaskThird);

        taskManager.deleteEpicById(6);

        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubtasks());







    }
}
