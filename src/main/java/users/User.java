package users;
import lombok.Getter;

@Getter
public class User {

    String eventName;
    Integer userId;
    String userLoginName;
    String userFullName;
    String userPassword;
    String userEmail;
    Integer phoneNumber;
    String packagePrice;
    String callStatus;
    boolean participation;


    public User(String userFullName, String userEmail, Integer phoneNumber, String userLoginName, String userPassword) {
        this.userFullName = userFullName;
        this.userEmail = userEmail;
        this.phoneNumber = phoneNumber;
        this.userLoginName = userLoginName;
        this.userPassword = userPassword;
    }

    public User(String userFullName, Integer phoneNumber) {
        this.userFullName = userFullName;
        this.phoneNumber = phoneNumber;
    }

    public User(String userFullName) {
        this.userFullName = userFullName;

    }

    public User(Integer userId, String userFullName) {
        this.userId = userId;
        this.userFullName = userFullName;
    }

    public User(int id, String login_name, String user_name, String email, int phone) {
    }

    public User(String userFullName, String eventName) {
        this.userFullName = userFullName;
        this.eventName = eventName;
    }

    public User(String eventName, Integer userId, String userFullName) {
        this.eventName = eventName;
        this.userId = userId;
        this.userFullName = userFullName;
    }

    @Override
    public String toString() {
        return  "Event: " + eventName+ "; Nr: " + userId + ") " + userFullName;
    }
}
