package customerData;

public class AppData {
    private static AppData appData_instance; //instance
    private  Integer loggedInUserId = null;

    AppData(){
    }

    public static AppData getInstance(){
        if(appData_instance == null) appData_instance = new AppData();
        return appData_instance;
    }

    public int getLoggedInUserId() {
        return this.loggedInUserId;
    }

    public void setLoggedInUserId(Integer loggedInUserId) {
        this.loggedInUserId = loggedInUserId;
    }

}
