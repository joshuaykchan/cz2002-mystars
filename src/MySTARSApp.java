import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class MySTARSApp {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        boolean loginSuccess = false;
        
        int choice;
        Scanner sc = new Scanner(System.in);

        String accountType = null;
        String username = null;
        String password = null;

        while (!loginSuccess) {
            do {
                System.out.println("Select your account type:");
                System.out.println("1. Student");
                System.out.println("2. Staff");
                choice = Integer.parseInt(sc.nextLine());    
                switch (choice) {
                    case 1:
                        accountType = "Student";
                        break;
                    case 2:
                        accountType = "Staff";
                        break;
                    default:
                        System.out.println("Invalid input! Please select a valid choice.");
                        break;
                }
            } while (choice != 1 && choice != 2);
            System.out.println("Enter your username: ");
            username = sc.nextLine();
            System.out.println("Enter your password: ");
            password = sc.nextLine();

            loginSuccess = LoginController.validate(accountType, username, password);

            if (!loginSuccess) System.out.println("Username or password incorrect.");
        }
        // StaffManager.addStaff(new Staff("admin", "password", 1));
    }
}
