package events;
import lombok.Getter;
import users.User;

import java.sql.Date;

@Getter
public class Event {
    String customerId;
    Integer eventId;
    String eventName;
    Date dueDate;
    String dueTime;
    String location;
    Integer guestNumber;

    public Event() {
    }

    public Event(String customerId, String eventName, Date dueDate, String dueTime, String location, Integer guestNumber) {
        this.customerId = customerId;
        this.eventName = eventName;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.location = location;
        this.guestNumber = guestNumber;
    }

    public Event(String eventName) {
        this.eventName = eventName;
    }

    public Event(Integer eventId, String eventName, Date dueDate, String dueTime, String location, Integer guestNumber) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.location = location;
        this.guestNumber = guestNumber;
    }

    public Event(String eventName, Date dueDate, String dueTime, String location, Integer guestNumber) {
        this.eventName = eventName;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.location = location;
        this.guestNumber = guestNumber;
    }



    @Override
    public String toString() {
        return eventName;

    }


}
