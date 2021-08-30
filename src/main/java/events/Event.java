package events;

import lombok.Getter;

import java.sql.Date;
import java.sql.Time;

@Getter
public class Event {
    Integer eventId;
    String eventName;
    Date dueDate;
    Time dueTime;
    String location;
    Integer guestNumber;

    public Event() {
    }

    public Event(Integer eventId, String eventName, Date dueDate, Time dueTime, String location, Integer guestNumber) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.location = location;
        this.guestNumber = guestNumber;
    }

    @Override
    public String toString() {
        return eventId +
                " \t | " + eventName +
                " \t | " + dueDate +
                " \t | " + dueTime +
                " \t | " + location +
                " \t | " + guestNumber;
    }
}
