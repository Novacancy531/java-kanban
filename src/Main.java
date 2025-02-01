public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");


        Task task1 = new Task("Завтрак","Позавтракать", Status.NEW);
        Task task2 = new Task("Обед","Пообедать", Status.NEW);
        Task task3 = new Task("Ужин","Поужинать", Status.NEW);
        Task task4 = new Task("Завтрак","Позавтракать", Status.DONE);
        Epic epic1 = new Epic("Список", "Важные дела");
        Epic epic2  = new Epic("Большой список", "Важные дела");
        Subtask subtask1 = new Subtask("Дело1", "Первое дело", Status.DONE);
        Subtask subtask2 = new Subtask("Дело2", "Второе дело", Status.IN_PROGRESS);
        Subtask subtask3 = new Subtask("Дело3", "Третье дело", Status.DONE);
        Subtask subtask4 = new Subtask("Дело2", "Второе дело", Status.DONE);

        TaskManager.addTask(task1);
        TaskManager.addTask(task2);
        TaskManager.addTask(task3);
        /*TaskManager.addEpic(epic1);
        epic1.addSubtask(subtask1);
        epic1.addSubtask(subtask2);
        epic1.addSubtask(subtask3);
        System.out.println(epic1.outputAllSubtasks());
        epic1.updateSubtask();*/

        System.out.println(TaskManager.outputAllTasks());

        TaskManager.updateTask(3, task4);

        System.out.println(TaskManager.outputAllTasks());






    }
}
