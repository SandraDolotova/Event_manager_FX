package decor;
import db.DBHandler;
import db.Queries;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DecorDBService {


    Connection connection = DBHandler.getConnection();

    public DecorDBService() throws Exception {
    }


    // INSERT DECORATION INTO TABLE
    public void insertNewDecor(String decorName, int decorQwt, double decorPrice, String decorStatus) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.insertNewDecor);
        pr.setString(1, decorName);
        pr.setInt(2, decorQwt);
        pr.setDouble(3, decorPrice);
        pr.setString(4, decorStatus);
        pr.execute();
        pr.close();
    }

    public void insertCustomerChosenDecor(Decor decor) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.insertCustomerChosenDecor);
        pr.setString(1, decor.getDecorName());
        pr.setInt(2, decor.getDecorQwt());
        pr.setString(3, decor.getCustomerId());
        pr.setString(4, decor.getEventName());
        // pr.setInt(5, decor.getDecorId());
        pr.execute();
        pr.close();
    }

    public void updateCustomerDecor() throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.updateCustomerDecor);
        pr.executeUpdate();
        pr.close();
    }

    //DELETE DECOR
    public void deleteDecor(int decorId) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.deleteDecor);
        pr.setInt(1, decorId);
        pr.execute();
        pr.close();
    }

    public void deleteCustomerDecor(String decorName) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.deleteCustomerDecor);
        pr.setString(1, decorName);
        pr.execute();
        pr.close();
    }


    // SHOW ALL DECORATIONS FOR ADMIN
    public ArrayList<Decor> showAllDecorAdmin() throws SQLException {
        ArrayList<Decor> decors = new ArrayList<>();
        PreparedStatement pr = connection.prepareStatement(Queries.showAllDecorAdmin);
        ResultSet result = pr.executeQuery();
        while (result.next()) {
            decors.add(new Decor(
                    result.getInt("decor_id"),
                    result.getString("decor_name"),
                    result.getInt("decor_qwt"),
                    result.getDouble("decor_price"),
                    result.getDouble("decor_price_vat"),
                    result.getString("decor_status")));
        }
        return decors;
    }


    // UPDATE DECOR IN TABLE:
    public void updateDecorPrice(int decorId, double newPrice) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.updateDecorPrice);
        pr.setDouble(1, newPrice);
        pr.setInt(2, decorId);
        pr.executeUpdate();
        pr.close();
    }

    public void updateDecorQuantity(int decorId, int newQuantity) throws SQLException {
        PreparedStatement pr = connection.prepareStatement(Queries.updateDecorQuantity);
        pr.setDouble(1, newQuantity);
        pr.setInt(2, decorId);
        pr.executeUpdate();
        pr.close();
    }

    // SHOW ALL DECORATIONS FOR CUSTOMER
    //if(decorStatus = available) -> showAllDecor();
    public ArrayList<Decor> showAllDecorCustomer() throws SQLException {
        ArrayList<Decor> decors = new ArrayList<>();
        PreparedStatement pr = connection.prepareStatement(Queries.showAllDecorCustomer);
        ResultSet result = pr.executeQuery();
        while (result.next()) {
            decors.add(new Decor(
                    result.getInt("decor_id"),
                    result.getString("decor_name"),
                    result.getDouble("decor_price_vat")));
        }
        pr.close();
        return decors;
    }




}


