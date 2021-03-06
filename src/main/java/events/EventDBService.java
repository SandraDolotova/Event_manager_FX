package events;

import customerData.AppData;
import db.DBHandler;
import db.Queries;
import users.User;
import users.UserDBService;
import java.sql.*;
import java.util.ArrayList;

public class EventDBService {
    UserDBService userDBService = new UserDBService();

    Connection connection = DBHandler.getConnection();

    public EventDBService() throws Exception{
    }

    // INSERT INTO EVENTS

    public void insertNewEventAdmin(String eventName, Date dueDate, String dueTime, String location, int guestNumber) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.insertNewEventAdmin);
        pr.setString(1, eventName);
        pr.setDate(2, dueDate);
        pr.setString(3, dueTime);
        pr.setString(4, location);
        pr.setInt(5, guestNumber);
        pr.execute();
        pr.close();
    }

    public void insertNewEventCustomer(Event event) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.insertNewEventCustomer);
        pr.setString(1, event.getCustomerId());
        pr.setString(2, event.getEventName());
        pr.setDate(3, event.getDueDate());
        pr.setString(4, event.getDueTime());
        pr.setString(5, event.getLocation());
        pr.setInt(6, event.getGuestNumber());
        pr.execute();
        pr.close();
    }

    // DELETE EVENT
    public void deleteEvent(int eventId) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.deleteEvent);
        pr.setInt(1, eventId);
        pr.execute();
        pr.close();
    }

    public void deleteCustomerEvent(String eventName) throws SQLException {
        PreparedStatement pr =connection.prepareStatement(Queries.deleteEventCustomer);
        pr.setString(1,eventName);
        pr.execute();
        pr.close();
    }


    // SHOW EVENT LIST for ADMIN
    public ArrayList<Event> showAllEvents() throws SQLException {
        ArrayList<Event> events = new ArrayList<>();
        PreparedStatement pr = connection.prepareStatement(Queries.showAllEvents);
        ResultSet result = pr.executeQuery();
        while (result.next()) {
            events.add(new Event(
                    result.getInt("event_id"),
                    result.getString("event_name"),
                    result.getDate("dueDate"),
                    result.getString("dueTime"),
                    result.getString("location_name"),
                    result.getInt("guests_number")));
        }
        pr.close();
        return events;
    }

    // UPDATE EVENT date
    public void updateEventName(int eventId, String newName) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.updateEventName);
        pr.setString(1, newName);
        pr.setInt(2, eventId);
        pr.executeUpdate();
        pr.close();
    }

    public void updateEventDate(int eventId, Date newDate) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.updateEventDate);
        pr.setDate(1, newDate);
        pr.setInt(2, eventId);
        pr.executeUpdate();
        pr.close();
    }

    public void updateEventTime(int eventId, String newTime) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.updateEventTime);
        pr.setString(1, newTime);
        pr.setInt(2, eventId);
        pr.executeUpdate();
        pr.close();
    }

    public void updateEventLocation(int eventId, String newLocation) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.updateEventLocation);
        pr.setString(1, newLocation);
        pr.setInt(2, eventId);
        pr.executeUpdate();
        pr.close();
    }

    public void updateEventGuestQuantity(int eventId, int newGuestQuantity) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.updateEventGuestQwt);
        pr.setInt(1, newGuestQuantity);
        pr.setInt(2, eventId);
        pr.executeUpdate();
        pr.close();
    }

    //TO SHOW INFO ABOUT ORDERED EVENT
    public ArrayList<Event> showOrderDetails() throws Exception {
        User user = userDBService.showLoggedInCustomer(AppData.getInstance().getLoggedInUserId());
        String sql = "SELECT event_id, event_name, dueDate, dueTime, location_name, guests_number FROM events WHERE  customer_id = '" + user.getUserId() + "'" ;
        ArrayList<Event> orders = new ArrayList<>();
        PreparedStatement pr = connection.prepareStatement(sql);
        ResultSet result = pr.executeQuery();

        while (result.next()){
            orders.add(new Event(
                    result.getInt("event_id"),
                    result.getString("event_name"),
                    result.getDate("dueDate"),
                    result.getString("dueTime"),
                    result.getString("location_name"),
                    result.getInt("guests_number")));
        }
        pr.close();
        return orders;
    }
    public ArrayList<Event> showCustomerEvents() throws Exception {
        User user = userDBService.showLoggedInCustomer(AppData.getInstance().getLoggedInUserId());
        String sql = "SELECT event_name FROM events WHERE customer_id = '" + user.getUserId() + "'" ;
        ArrayList<Event> events = new ArrayList<>();
        PreparedStatement pr = connection.prepareStatement(sql);
        ResultSet result = pr.executeQuery();
        while (result.next()){
            events.add(new Event(
                    result.getString("event_name")));
        }
        pr.close();
        return events;
    }

    
}