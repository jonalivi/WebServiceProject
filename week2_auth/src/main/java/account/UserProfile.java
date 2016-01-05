package account;

/**
 * Created by Надежда Полидорова on 05.01.2016.
 */
public class UserProfile {
    private String login;
    private String password;
    private String email;

    public UserProfile(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public UserProfile(String login) {
        this.login = login;
        this.password = login;
        this.email = login;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
