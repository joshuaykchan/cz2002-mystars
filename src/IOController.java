import java.io.*;
import java.util.ArrayList;

public class IOController <T> {

    public static <T> void writeFile(String filename, ArrayList<T> list) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
        oos.writeObject(list);
    }

    public static <T> ArrayList<T> readFile(String filename) throws IOException, ClassNotFoundException {
        ArrayList<T> list = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
            list = (ArrayList<T>) ois.readObject();
        } catch (Exception e) {
            list = new ArrayList<T>();
        }
        return list;
    }

}
