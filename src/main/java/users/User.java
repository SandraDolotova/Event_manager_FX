package users;
import lombok.Getter;

@Getter
public class User {

    Integer userId;
    String userLoginName;
    String userFullName;
    String userPassword;
    String userEmail;
    String phoneNumber;
    String packagePrice;
    String callStatus;
    boolean participation;

    public User(String userFullName, String userEmail, String phoneNumber, String userLoginName, String userPassword) {
        this.userFullName = userFullName;
        this.userEmail = userEmail;
        this.phoneNumber = phoneNumber;
        this.userLoginName = userLoginName;
        this.userPassword = userPassword;
    }

    public User(String userFullName, String phoneNumber) {
        this.userFullName = userFullName;
        this.phoneNumber = phoneNumber;

    }

    public User(int id, String login_name, String user_name, String email, int phone) {
    }

    public User(int guest_id, String guest_name, boolean participation) {
    }

}
