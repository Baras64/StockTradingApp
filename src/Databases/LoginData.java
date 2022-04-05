package Databases;

public class LoginData {
    private String username;
    private String password;
    private String emailID;

    public LoginData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginData(String username, String password, String emailID) {
        this.username = username;
        this.password = password;
        this.emailID = emailID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmailID() {
        return emailID;
    }
}
