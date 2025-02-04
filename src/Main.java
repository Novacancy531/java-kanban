public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        Task taskFirst = new Task("Завтрак", "Позавтракать", Status.IN_PROGRESS);
        Task taskSecond = new Task("Обед", "Пообедать", Status.DONE);
        Epic epicFirst = new Epic("Ужин", "Поужинать");
        Subtask subtaskFirst = new Subtask("Есть", "Есть гречку", Status.DONE);
        Subtask subtaskSecond = new Subtask("Пить", "Пить компот", Status.IN_PROGRESS);
        Epic epicSecond = new Epic("Полдник", "Полдничать");
        Subtask subtaskThird = new Subtask("Есть", "Есть печенье", Status.NEW);

        TaskManager.addTask(taskFirst);
        TaskManager.addTask(taskSecond);
        TaskManager.addTask(epicFirst);
        TaskManager.addTask(subtaskFirst, epicFirst);
        TaskManager.addTask(subtaskSecond, epicFirst);
        TaskManager.addTask(epicSecond);
        TaskManager.addTask(subtaskThird, epicSecond);

        Task taskUpdater1 = new Task ("Первое", "первое обновление", Status.DONE);
        Subtask subtaskUpdater = new Subtask("Второе", "второе обновление", Status.DONE);
        Epic epicUpdater = new Epic("Третье", "Третье обновление");


        TaskManager.updateTask(taskUpdater1, 1);
       // TaskManager.updateTask(subtaskUpdater, 1);
       // TaskManager.updateTask(epicUpdater, 2);

        System.out.println(TaskManager.getAllTasks());

    }
}
