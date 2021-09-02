package events;

import db.DBHandler;
import db.Queries;

import java.sql.*;
import java.util.ArrayList;

public class EventDBService {
    Connection connection = DBHandler.getConnection();

    // INSERT INTO EVENTS
    public void insertNewEvent(Event event) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.insertNewEvent);
        pr.setString(1, event.getEventName());
        pr.setDate(2, event.getDueDate());
        pr.setString(3, event.getDueTime());
        pr.setString(4, event.getLocation());
        pr.setInt(5, event.getGuestNumber());
        pr.execute();
        pr.close();
    }

    // DELETE EVENT
    public void deleteEvent(int eventId) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.deleteEvent);
        pr.setInt(1, eventId);
        pr.executeUpdate();
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

    // SHOW ONE EVENT
    public Event showSingleEvent(int eventId) throws SQLException {
        Event event = new Event();
        PreparedStatement pr = connection.prepareStatement(Queries.showSingleEvent);
        pr.setInt(1, eventId);
        ResultSet result = pr.executeQuery();
        if (result.next()) {
            event = new Event(
                    result.getInt("event_id"),
                    result.getString("event_name"),
                    result.getDate("dueDate"),
                    result.getString("dueTime"),
                    result.getString("location_name"),
                    result.getInt("guests_number"));
            pr.close();
        }
        return event;
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

    public void updateEventTime(int eventId, Time newTime) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.updateEventTime);
        pr.setTime(1, newTime);
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
        PreparedStatement pr = connection.prepareStatement(Queries.updateEventGuests);
        pr.setInt(1, newGuestQuantity);
        pr.setInt(2, eventId);
        pr.executeUpdate();
        pr.close();
    }
}