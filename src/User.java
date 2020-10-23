import java.io.Serializable;

public class User implements Serializable {

    private String username;
    private String password;
    private String accountType;
    private static final long serialVersionUID = 1;

    public User(String username, String password, String accountType) {
        this.username = username;
        this.password = password;
        this.accountType = accountType;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getAccountType() {
        return this.accountType;
    }
}