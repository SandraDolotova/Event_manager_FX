package users;

import packageTypes.PackagePrice;
import customerData.AppData;
import db.DBHandler;
import db.Queries;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class UserDBService {

    Connection connection = DBHandler.getConnection();

    public UserDBService() throws Exception {
    }

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

    public Integer showLoggedIn(String login, String pass) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.showLoggedIn);
        pr.setString(1, login);
        pr.setString(2, pass);
        ResultSet result = pr.executeQuery();

        Integer userId = null;
        if (result.next()) userId = result.getInt("id");

        return userId;
    }

    public User showLoggedInCustomer(int loggedInUserId) throws Exception {
        PreparedStatement pr = connection.prepareStatement(Queries.showLoggedInCustomer);
        pr.setInt(1, loggedInUserId);
        ResultSet result = pr.executeQuery();

        User user = null;
        if (result.next()) {
            user = new User(
                    result.getInt("id"),
                    result.getString("user_full_name"));
        }
        return user;
    }

    //to register new user in DB
    public void addNewUser(User user) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.insertNewUser);
        pr.setString(1, user.getUserFullName());
        pr.setString(2, user.getUserEmail());
        pr.setInt(3, user.getPhoneNumber());
        pr.setString(4, user.getUserLoginName());
        pr.setString(5, user.getUserPassword());
        pr.execute();
        pr.close();
    }

    public void addCallBackConcept(String userFullName, int phone, String packagePrice) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.insertCallBackPhone);
        pr.setString(1, userFullName);
        pr.setInt(2, phone);
        pr.setString(3, packagePrice);
       // pr.setString(3, String.valueOf(PackagePrice.CONCEPT));
        pr.execute();
        pr.close();
    }

    public void packagePrice(PackagePrice packagePrice) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.insertPackagePrice);
        pr.setString(1, Arrays.toString(PackagePrice.values()));
        pr.execute();
        pr.close();
    }

    // to show all existing users to Admin
    public ArrayList<User> showUsers() throws SQLException {
        {
            ArrayList<User> users = new ArrayList<>();
            PreparedStatement pr = connection.prepareStatement(Queries.showUserList);
            ResultSet result = pr.executeQuery();
            while (result.next()) {
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
    public void insertGuests(User user) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.insertGuests);
        pr.setString(3, user.getUserFullName());
        pr.setString(1, user.getEventName());
        pr.setInt(2, AppData.getInstance().getLoggedInUserId());
        pr.execute();
        pr.close();
    }

    // DELETE FROM GUEST LIST
    public void deleteGuest(User user) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.deleteGuest);
        pr.setString(1, user.getUserFullName());
        pr.executeUpdate();
        pr.close();
    }
    // UPDATE GUEST LIST
    public void updateGuestLIst() throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.updateEventGuests);
        pr.executeUpdate();
        pr.close();
    }

    public void setGuestStatus(String eventName) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.setGuestStatus);
        pr.setString(1, eventName);
        pr.executeUpdate();
        DBHandler.close(pr, connection);
    }

    // SHOW ALL GUESTS FROM THE LIST
    public ArrayList<User> showAllGuests() throws Exception {
      // String sql = "SELECT * FROM event_guest_list WHERE customer_id = '" + showLoggedInCustomer(AppData.getInstance().getLoggedInUserId()) + "'" ;
       // String sql = "SELECT * FROM event_guest_list WHERE event_name = "
        ArrayList<User> users = new ArrayList<>();
        PreparedStatement pr = connection.prepareStatement(Queries.showAllGuests);
      //  PreparedStatement pr = connection.prepareStatement(sql);
        ResultSet result = pr.executeQuery();
        while (result.next()) {
            users.add(new User(
                    result.getInt("guest_id"),
                    result.getString("guest_name")));
            //  result.getBoolean("participation")));
        }
        pr.close();
        return users;
    }


}
