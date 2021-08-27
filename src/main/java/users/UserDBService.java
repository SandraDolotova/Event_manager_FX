package users;
import Types.PackagePrice;
import db.DBHandler;
import db.Queries;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserDBService {
    Connection connection = DBHandler.getConnection();

    // to get user login name and password from DB for login validation
    public Map<String, String> userCheck() throws SQLException {
        Map<String, String> mapResult = new HashMap<>();
        if (connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(Queries.userValidation);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String nameKey = resultSet.getString("login_name");
                    String passValue = resultSet.getString("user_password");
                    mapResult.put(nameKey, passValue);
                }
              //  connection.close();
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }
        return mapResult;
    }

    //to register new user in DB
    public void addNewUser(User user) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.insertNewUser);
        pr.setString(1, user.getUserFullName());
        pr.setString(2, user.getUserEmail());
        pr.setString(3, user.getPhoneNumber());
        pr.setString(4, user.getUserLoginName());
        pr.setString(5, user.getUserPassword());
        pr.execute();
        pr.close();
    }

    //to register phone number for call back
    public void addCallBack(User user) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.insertCallBackPhone);
        pr.setString(1, user.getUserFullName());
        pr.setString(2, user.getPhoneNumber());
       // pr.setString(3, packagePrice());
        pr.execute();
        pr.close();
    }



    // to show all existing users to Admin
    public ArrayList<User> showUsers () throws SQLException { {
        ArrayList<User> users = new ArrayList<>();
        PreparedStatement pr = connection.prepareStatement(Queries.showUserList);
        ResultSet result = pr.executeQuery();
        while (result.next()){
            users.add(new User(
                    result.getInt("id"),
                    result.getString("login_name"),
                    result.getString("user_name"),
                    result.getString("email"),
                    result.getInt("phone")));
        }
        DBHandler.close(pr, connection);
        return users;
    }
    }
    // INSERT INTO GUEST LIST - customer inserts names
    public void insertGuests (String guestFullName) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.insertGuests);
        pr.setString(1, guestFullName);
        pr.execute();
        DBHandler.close(pr, connection);
    }

    // DELETE FROM GUEST LIST
    public void deleteGuest(int guestId) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.deleteGuest);
        pr.setInt(1, guestId);
        pr.executeUpdate();
        DBHandler.close(pr, connection);
    }
    // UPDATE GUEST LIST - customer sets participation status for his guests
    //
    public void setGuestStatus(String eventName) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.setGuestStatus);
        pr.setString(1, eventName);
        pr.executeUpdate();
        DBHandler.close(pr, connection);
    }

    // SHOW ALL GUESTS FROM THE LIST
    public static final String showAllGuests = "SELECT * FROM event_guest_list";
    public ArrayList<User> showAllGuests() throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        PreparedStatement pr = connection.prepareStatement(Queries.showAllGuests);
        ResultSet result = pr.executeQuery();
        while (result.next()){
            users.add(new User(
                    result.getInt("guest_id"),
                    result.getString("guest_name"),
                    result.getBoolean("participation")));
        }
        DBHandler.close(pr, connection);
        return users;
    }


}
