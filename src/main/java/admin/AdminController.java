/*package admin;

import decor.Decor;
import decor.DecorDBService;
import events.Event;
import events.EventDBService;

import javax.swing.*;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class AdminController {
    DecorDBService decorDBService = new DecorDBService();
    EventDBService eventDBService = new EventDBService();

    private final String decorListTitle = "ID \t| Name \t| Quantity \t| Price \t| Price vat \t| Status\n\n";
    private final String eventListTitle = "ID \t| Name \t| Date \t| Time \t| Location \t| Guests\n\n";

    //ALL METHODS ABOUT ADMIN CONTROLLING DECORATIONS:
    //methods that works:
    public void addDecorations(){
        try {
            String name = JOptionPane.showInputDialog(null, "Decor name:");
            int quantity = Integer.parseInt(JOptionPane.showInputDialog(null, "Decor quantity:"));
            double price = Double.parseDouble(JOptionPane.showInputDialog(null, "Decor price:"));
            String status = JOptionPane.showInputDialog(null, "Decor status:");
            decorDBService.insertNewDecor(name, quantity, price, status);
            JOptionPane.showMessageDialog(null,"New decor added successfully!");
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while adding decor");
        }
    }
    public void deleteDecoration(){
        String info = "Enter decor ID:";
        int decorId = Integer.parseInt(JOptionPane.showInputDialog(info));
        try {
            decorDBService.deleteDecor(decorId);
            JOptionPane.showMessageDialog(null, "Decor deleted successfully!");
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while deleting decor");
        }
    }
    public void getAllDecorsFromDB(){
        ArrayList<Decor> decorList = new ArrayList<>();
        try {
            decorList = decorDBService.showAllDecorAdmin();
        }catch (SQLException e){
            e.printStackTrace();
        }
        String message = decorListTitle + decorList.stream().map(Decor::toString).collect(Collectors.joining("\n"));
        JOptionPane.showMessageDialog(null, message);
    }
    public void getSingleDecorFromDB(){
        String decorId = JOptionPane.showInputDialog(null, "Enter decor ID:");
        Decor decor = null;
        try {
            decor = decorDBService.showSingleDecor(Integer.parseInt(decorId));
        }catch (SQLException e){
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, decorListTitle + decor);
    }
    public void updatePrice(){
        try {
            int decorId = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter decor ID you want to update:"));
            double newPrice = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter new price:"));
            decorDBService.updateDecorPrice(decorId, newPrice);
            JOptionPane.showMessageDialog(null, "Decor price updated successfully!");
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while updating decor price");
        }
    }
    public void updateQuantity(){
        try {
            int decorId = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter decor ID you want to update:"));
            int newQuantity = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter new quantity:"));
            decorDBService.updateDecorQuantity(decorId, newQuantity);
            JOptionPane.showMessageDialog(null, "Decor quantity updated successfully!");
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while updating decor quantity");
        }
    }


    //methods that doesn't work:
    public void decorStatus(){
        String info = "Enter decor ID:";
        int decorId  = Integer.parseInt(JOptionPane.showInputDialog(info));
        String decorStatus = "Enter decor status:";
        try {
            decorDBService.setDecorStatus(decorId, decorStatus);
            JOptionPane.showMessageDialog(null, "Decor status updated successfully!");
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while updating decor status");

        }
    }


    //ALL METHODS ABOUT ADMIN CONTROLLING EVENT:
    //method that works:
    public void addEvent(){
        try {
            String name = JOptionPane.showInputDialog(null, "Enter Event Name:");
            Date date = Date.valueOf(JOptionPane.showInputDialog(null, "Enter event date (YYYY-MM-DD):"));
            Time time = Time.valueOf(JOptionPane.showInputDialog(null, "Enter event time (HH:MM:SS):"));
            String location = JOptionPane.showInputDialog(null, "Enter event location:");
            int guestNumber = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter guest quantity:"));
            eventDBService.insertNewEvent(name, date, time, location, guestNumber);
            JOptionPane.showMessageDialog(null, "New Event Added successfully!");
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while adding event");
        }
    }
    public void deleteEventFromDB(){
        String info = "Enter Event ID:";
        int eventId = Integer.parseInt(JOptionPane.showInputDialog(info));
        try {
            eventDBService.deleteEvent(eventId);
            JOptionPane.showMessageDialog(null, "Event deleted successfully!");
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while deleting event");
        }
    }
    public void getAllEventsFromDB(){
        ArrayList<Event> eventList = new ArrayList<>();
        try {
            eventList = eventDBService.showAllEvents();
        }catch (SQLException e){
            e.printStackTrace();
        }
        String message = eventListTitle + eventList.stream().map(Event::toString).collect(Collectors.joining("\n"));
        JOptionPane.showMessageDialog(null, message);
    }
    public void getSingleEventFromDB(){
        String eventId = JOptionPane.showInputDialog(null, "Enter event ID:");
        Event event = null;
        try {
            event = eventDBService.showSingleEvent(Integer.parseInt(eventId));
        }catch (SQLException e){
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, eventListTitle + event);
    }
    public void updateNameEvent(){
        try {
            int eventId = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter event ID you want to update:"));
            String newName = JOptionPane.showInputDialog(null, "Enter Event new name:");
            eventDBService.updateEventName(eventId, newName);
            JOptionPane.showMessageDialog(null, "Event Name updated successfully!");
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while updating event name");
        }
    }
    public void updateDate(){
        try {
            int eventId = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter event ID you want to update:"));
            Date newDate = Date.valueOf(JOptionPane.showInputDialog(null, "Enter new date(YYYY-MM-DD):"));
            eventDBService.updateEventDate(eventId, newDate);
            JOptionPane.showMessageDialog(null, "Event Date updated successfully!");
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while updating event date");
        }
    }
    public void updateTime(){
        try {
            int eventId = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter event ID you want to update:"));
            Time newTime = Time.valueOf(JOptionPane.showInputDialog(null, "Enter new time(HH:MM:00):"));
            eventDBService.updateEventTime(eventId, newTime);
            JOptionPane.showMessageDialog(null, "Event Time updated successfully!");
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while updating event time");
        }
    }
    public void updateLocation(){
        try {
            int eventId = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter event ID you want to update:"));
            String newLocation = JOptionPane.showInputDialog(null, "Enter Event new location:");
            eventDBService.updateEventLocation(eventId, newLocation);
            JOptionPane.showMessageDialog(null, "Event location updated successfully!");
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while updating event location");
        }
    }
    public void updateGuestAmount(){
        try {
            int eventId = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter event ID you want to update:"));
            int newGuestAmount = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Event new guest amount:"));
            eventDBService.updateEventGuestQuantity(eventId, newGuestAmount);
            JOptionPane.showMessageDialog(null, "Event guest amount updated successfully!");
        }catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while updating event guest amount");
        }
    }

}
*/