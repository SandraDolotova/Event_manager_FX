package db;

public class Queries {

    //USERS
    public static final String userValidation = "SELECT login_name, user_password FROM user_validation";
    public static final String insertNewUser = "INSERT INTO user_validation (user_full_name, email, phone, login_name, user_password) VALUES (?, ?, ?, ?, ?)";
    public static final String insertCallBackPhone = "INSERT  INTO call_back (user_full_name, phone) VALUES (?, ?)";
    public static String insertPackagePrice = "INSERT INTO call_back (package_price) VALUES (?)";
    public static final String insertCallStatus = "INSERT INTO call_back (call_status) VALUES (?)";
    public static final String showUserList = "SELECT id, login_name, user_name, phone, email FROM user_validation";

    //DECOR
    // INSERT DECORATION INTO TABLE

    public static String insertCustomerChosenDecor = "INSERT INTO customer_decor (customer_decor_id, customer_decor_name, customer_decor_qwn, customer_decor_price_vat) VALUES (?, ?, ?, ?)";
    public static final String insertNewDecor = "INSERT INTO decor_list (decor_name, decor_qwt, decor_price) VALUES (?, ?, ?)";
    //SET DECOR STATUS - value chosen by ADMIN from ComboBox = like out of stock, broken, not available
   // ??????
    public static final String setDecorStatus = "INSERT INTO decor_list (decor_status) VALUES (?) WHERE decor_id = ?";
    //DELETE DECOR
    public static final String deleteDecor = "DELETE FROM decor_list WHERE decor_id = ?";
    // UPDATE DECOR PRICE IN TABLE
    //?????????
    public static final String updateDecorPrice = "UPDATE decor_list SET decor_price = ? WHERE decor_id = ?";
    public static final String updateDecorQuantity = "UPDATE decor_list SET decor_qwt = ? WHERE decor_id = ?";
    // SHOW ALL DECORATIONS FOR CUSTOMER
    public static final String showAllDecorCustomer = "SELECT decor_id, decor_name, decor_price_vat FROM decor_list";
    // SHOW ALL DECORATIONS FOR ADMIN
    public static final String showAllDecorAdmin = "SELECT decor_id, decor_name, decor_qwt, decor_price, decor_price_vat, decor_status FROM decor_list";
    // SHOW SINGLE DECOR ITEM BY ID
    public static final String showSingleDecorByID = "SELECT decor_id, decor_name, decor_qwt, decor_price_vat FROM decor_list WHERE decor_id = ?";


    //EVENTS
    // INSERT INTO EVENTS
    public static final String insertNewEvent = "INSERT INTO events (event_name, dueDate, location_name, guests_number) VALUES (?, ?, ?, ?)";
    // DELETE EVENT
    public static final String deleteEvent = "DELETE FROM events WHERE event_name = ?";
    // UPDATE EVENT date
   // public static final String updateEventDate = "UPDATE events SET dueDate = ? WHERE event_name = ?";
    // SHOW EVENT LIST for ADMIN
    public static final String showAllEvents = "SELECT * FROM events";
    // SHOW ONE EVENT
    public static final String showSingleEvent = "SELECT event_id, event_name, dueDate, location_name, guests_number FROM events WHERE event_name = ?";
    public static final String updateEventName = "UPDATE events SET event_name = ? WHERE event_id = ?";
    public static final String updateEventDate = "UPDATE events SET dueDate = ? WHERE event_id = ?";
    public static final String updateEventTime = "UPDATE events SET dueTime = ? WHERE event_id = ?";
    public static final String updateEventLocation = "UPDATE events SET location_name = ? WHERE event_id = ?";
    public static final String updateEventGuests = "UPDATE events SET guests_number = ? WHERE event_id = ?";


    //GUESTS
    // INSERT INTO GUEST LIST - customer inserts names
    public static final String insertGuests = "INSERT INTO event_guest_list (guest_name) VALUES (?)";
    // DELETE FROM GUEST LIST
    public static final String deleteGuest = "DELETE FROM event_guest_list WHERE guest_id = ?";
    // UPDATE GUEST LIST - customer sets participation status for his guests
    public static final String setGuestStatus = "UPDATE event_guest_list SET participation = ? WHERE event_name = ?";
    // SHOW ALL GUESTS FROM THE LIST
    public static final String showAllGuests = "SELECT * FROM event_guest_list";
    //SHOW ALL GUESTS = true
    public static final String showTrueGuests = "SELECT guest_name FROM event_guest_list WHERE participation = 1";
    //SHOW ALL GUESTS = false
    public static final String showFalseGuests = "SELECT guest_name FROM event_guest_list WHERE participation = 0";



    //BILL
    // INSERT INTO BILL INFO FOR PAYMENT
    // SHOW BILL
    // SHOW ALL PAYMENTS


}
