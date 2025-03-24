package assistants;

import java.io.File;
import java.io.IOException;

public final class Initialize {

    /**
     * Путь к файлу хранилищу.
     */
    private static String dataPath;
    /**
     * Файл для хранения задач.
     */
    private static File fileStorage;


    private Initialize() {
    }

    /**
     * Метод для создания файла хранения задач или возвращения если он уже существует.
     *
     * @return файл для хранения задач.
     */
    public static File initializeStorage() {
        dataPath = "src\\storage\\taskStorage.csv";
        fileStorage = new File(dataPath);


        try {
            if (!(fileStorage.exists() && fileStorage.isFile())) {
                boolean fileCreated = fileStorage.createNewFile();
                if (!fileCreated) {
                    throw new IOException("Ошибка создания файла: " + dataPath);
                }
            }
            return fileStorage;
        } catch (IOException e) {
            System.out.println("Ошибка инициализации файла-хранилища: " + e.getMessage());
            return fileStorage;
        }
    }

    /**
     * Метод для создания файла задач или возвращения файла если он уже существует по выбранному пути.
     *
     * @param path путь до файла.
     * @return файл.
     */
    public static File initializeStorage(final String path) {
        dataPath = path;
        fileStorage = new File(dataPath);


        try {
            if (!(fileStorage.exists() && fileStorage.isFile())) {
                boolean fileCreated = fileStorage.createNewFile();
                if (!fileCreated) {
                    throw new IOException("Ошибка создания файла: " + dataPath);
                }
            }
            return fileStorage;
        } catch (IOException e) {
            System.out.println("Ошибка инициализации файла-хранилища: " + e.getMessage());
            return null;
        }
    }


}
