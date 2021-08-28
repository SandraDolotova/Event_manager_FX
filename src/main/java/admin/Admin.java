package admin;

public class Admin {
    String email;
    String password;

    public Admin(){}

    public Admin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    //need to change this
    @Override
    public String toString() {
        return email + "\t" + password;
    }
}
