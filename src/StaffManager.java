import java.io.*;
import java.util.ArrayList;

public class StaffManager {

    private static ArrayList<Staff> staffList;
    private static final String filename = "src/data/Staff.dat";

    public static void addStaff(Staff staff) throws IOException, ClassNotFoundException {
        staffList = IOController.readFile(filename);
        staffList.add(staff);
        IOController.writeFile(filename, staffList);
    }

    public static void removeStaff(String username) throws IOException, ClassNotFoundException {
        staffList = IOController.readFile(filename);
        Staff toDelete = null;
        for (Staff s: staffList) {
            if (s.getUsername().equals(username)) {
                toDelete = s;
                break;
            }
        }
        if (toDelete != null) staffList.remove(toDelete);
        IOController.writeFile(filename, staffList);
    }
}