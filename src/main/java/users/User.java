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
    String paymentStatus;

    public User(){}

    public User(String userFullName, String userEmail, Integer phoneNumber, String userLoginName, String userPassword) {
        this.userFullName = userFullName;
        this.userEmail = userEmail;
        this.phoneNumber = phoneNumber;
        this.userLoginName = userLoginName;
        this.userPassword = userPassword;
    }

    public User(Integer userId, String userFullName) {
        this.userId = userId;
        this.userFullName = userFullName;
    }

    public User(int userId, String userFullName, String userEmail, int phoneNumber) {
        this.userId = userId;
        this.userFullName = userFullName;
        this.userEmail = userEmail;
        this.phoneNumber = phoneNumber;
    }

    public User(String eventName, Integer userId, String userFullName) {
        this.eventName = eventName;
        this.userId = userId;
        this.userFullName = userFullName;
    }

    public User(Integer userId, String userFullName, Integer phoneNumber, String packagePrice, String callStatus) {
        this.userId = userId;
        this.userFullName = userFullName;
        this.phoneNumber = phoneNumber;
        this.packagePrice = packagePrice;
        this.callStatus = callStatus;
    }


    @Override
    public String toString() {
        return
                "Nr: " + userId + ") " + userFullName;
    }
}
