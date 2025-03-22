package exceptions;

public class ManagerSaveException extends RuntimeException {

    /**
     * Конструктор по умолчанию.
     *
     * @param message сообщение об ошибке.
     */
    public ManagerSaveException(final String message) {
        super(message);
    }
}
