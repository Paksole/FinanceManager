import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileManager {
    private static final String DATA_DIR = "data";
    private static final String USERS_FILE = DATA_DIR + "/users.dat";

    public static void saveUsers(Map<String, User> users) {
        try {
            new File(DATA_DIR).mkdirs();
            FileOutputStream fos = new FileOutputStream(USERS_FILE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(users);
            oos.close();
            fos.close();
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении данных: " + e.getMessage());
        }
    }

    public static Map<String, User> loadUsers() {
        Map<String, User> users = new HashMap<>();
        File file = new File(USERS_FILE);

        if (file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(USERS_FILE);
                ObjectInputStream ois = new ObjectInputStream(fis);
                users = (Map<String, User>) ois.readObject();
                ois.close();
                fis.close();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Ошибка при загрузке данных: " + e.getMessage());
            }
        }
        return users;
    }
}