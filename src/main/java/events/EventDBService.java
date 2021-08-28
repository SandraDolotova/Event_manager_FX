package events;

import db.DBHandler;
import db.Queries;

import java.sql.*;
import java.util.ArrayList;

public class EventDBService {
    DBHandler dbHandler = new DBHandler();

    // INSERT INTO EVENTS
    public void insertNewEvent(String eventName, Date dueDate, Time dueTime, String location, int guestNumber) throws SQLException {
        PreparedStatement pr = dbHandler.getConnection().prepareStatement(Queries.insertNewEvent);
        pr.setString(1, eventName);
        pr.setDate(2, dueDate);
        pr.setTime(3, dueTime);
        pr.setString(4, location);
        pr.setInt(5, guestNumber);
        pr.execute();
        pr.close();
    }
    // DELETE EVENT
    public void deleteEvent(int eventId) throws SQLException {
        PreparedStatement pr = dbHandler.getConnection().prepareStatement(Queries.deleteEvent);
        pr.setInt(1, eventId);
        pr.executeUpdate();
        pr.close();
    }
    // SHOW EVENT LIST for ADMIN
    public ArrayList<Event> showAllEvents () throws SQLException {
        ArrayList<Event> events = new ArrayList<>();
        PreparedStatement pr = dbHandler.getConnection().prepareStatement(Queries.showAllEvents);
        ResultSet result = pr.executeQuery();
        while (result.next()) {
            events.add(new Event(
                    result.getInt("event_id"),
                    result.getString("event_name"),
                    result.getDate("dueDate"),
                    result.getTime("dueTime"),
                    result.getString("location_name"),
                    result.getInt("guests_number")));
        }
        pr.close();
        return events;
    }
    // SHOW ONE EVENT
    public Event showSingleEvent(int eventId) throws SQLException {
        Event event = new Event();
        PreparedStatement pr = dbHandler.getConnection().prepareStatement(Queries.showSingleEvent);
        pr.setInt(1, eventId);
        ResultSet result = pr.executeQuery();
        if(result.next()){
            event = new Event(
                    result.getInt("event_id"),
                    result.getString("event_name"),
                    result.getDate("dueDate"),
                    result.getTime("dueTime"),
                    result.getString("location_name"),
                    result.getInt("guests_number"));
            pr.close();
        }
        return event;
    }
    // UPDATE EVENT date
    public void updateEventName(int eventId, String newName) throws SQLException{
        PreparedStatement pr = dbHandler.getConnection().prepareStatement(Queries.updateEventName);
        pr.setString(1, newName);
        pr.setInt(2, eventId);
        pr.executeUpdate();
        pr.close();
    }
    public void updateEventDate(int eventId, Date newDate) throws SQLException {
        PreparedStatement pr = dbHandler.getConnection().prepareStatement(Queries.updateEventDate);
        pr.setDate(1, newDate);
        pr.setInt(2, eventId);
        pr.executeUpdate();
        pr.close();
    }
    public void updateEventTime(int eventId, Time newTime) throws SQLException {
        PreparedStatement pr = dbHandler.getConnection().prepareStatement(Queries.updateEventTime);
        pr.setTime(1, newTime);
        pr.setInt(2, eventId);
        pr.executeUpdate();
        pr.close();
    }
    public void updateEventLocation(int eventId, String newLocation) throws SQLException {
        PreparedStatement pr = dbHandler.getConnection().prepareStatement(Queries.updateEventLocation);
        pr.setString(1, newLocation);
        pr.setInt(2, eventId);
        pr.executeUpdate();
        pr.close();
    }
    public void updateEventGuestQuantity(int eventId, int newGuestQuantity) throws SQLException {
        PreparedStatement pr = dbHandler.getConnection().prepareStatement(Queries.updateEventGuests);
        pr.setInt(1, newGuestQuantity);
        pr.setInt(2, eventId);
        pr.executeUpdate();
        pr.close();
    }
}
