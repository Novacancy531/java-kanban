import interfaces.TaskManager;
import managers.InMemoryTaskManager;

@SuppressWarnings("checkstyle:JavadocPackage")
public class Main {

    @SuppressWarnings({"checkstyle:MissingJavadocMethod", "checkstyle:FinalParameters"})
    public static void main(final String[] args) {
        System.out.println("Поехали!");

        TaskManager taskManager = new InMemoryTaskManager();

        taskManager.getTaskById(3);
    }
}
