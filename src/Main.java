public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        Task task1 = new Task("Завтрак","Позавтракать", Status.NEW);
        Task task2 = new Task("Обед","Пообедать", Status.NEW);
        Task task3 = new Task("Ужин","Поужинать", Status.NEW);

        TaskManager.addTask(task1);
        TaskManager.addTask(task2);
        TaskManager.addTask(task3);
        TaskManager.printAll();
    }
}
