import java.io.*;
import java.util.ArrayList;

public class StudentManager {

    private static ArrayList<Student> studentList;
    private static final String filename = "src/data/Students.dat";

    public static void addUser(Student student) throws IOException, ClassNotFoundException {
        studentList = IOController.readFile(filename);
        studentList.add(student);
        IOController.writeFile(filename, studentList);
    }

    public static void removeUser(String username) throws IOException, ClassNotFoundException {
        studentList = IOController.readFile(filename);
        Student toDelete = null;
        for (Student s: studentList) {
            if (s.getUsername().equals(username)) {
                toDelete = s;
                break;
            }
        }
        if (toDelete != null) studentList.remove(toDelete);
        IOController.writeFile(filename, studentList);
    }
}