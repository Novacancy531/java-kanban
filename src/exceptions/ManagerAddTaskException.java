package exceptions;

public class ManagerAddTaskException extends RuntimeException {

    /**
     * Конструктор по умолчанию.
     *
     * @param message сообщение об ошибке.
     */
    public ManagerAddTaskException(final String message) {
        super(message);
    }
}
