package decor;

import events.Event;
import lombok.Getter;

@Getter
public class Decor {
    String eventName;
    String customerId;
    int decorId;
    String decorName;
    int decorQwt;
    double decorPrice;
    double decorPriceVAT;
    double totalDecorPrice;
    String decorStatus;
    double transportCosts;
    double totalBill;

    public Decor(){}

    public Decor(String decorName, int decorQwt, String customerId, String eventName) {
        this.decorName = decorName;
        this.decorQwt = decorQwt;
        this.customerId = customerId;
        this.eventName = eventName;
    }

    public Decor(String decorName, int decorQwt) {
        this.decorName = decorName;
        this.decorQwt = decorQwt;
    }
    public Decor(int decorId, String decorName, int decorQwt, double decorPrice, double decorPriceVAT, String decorStatus) {
        this.decorId = decorId;
        this.decorName = decorName;
        this.decorQwt = decorQwt;
        this.decorPrice = decorPrice;
        this.decorPriceVAT = decorPriceVAT;
        this.decorStatus = decorStatus;
    }
    public Decor(int decorId, String decorName, double decorPriceVAT) {
        this.decorId = decorId;
        this.decorName = decorName;
        this.decorPriceVAT = decorPriceVAT;
    }
    public Decor(int decorId, String decorName, int decorQwt, double decorPrice) {
        this.decorId = decorId;
        this.decorName = decorName;
        this.decorQwt = decorQwt;
        this.decorPrice = decorPrice;
    }

   public Decor(String decorName, int parseInt, String eventIdToDB, String eventName, Integer decorId) {
   }

    public Decor(int decorId, String decorName, int decorQwt, double decorPriceVAT, double totalDecorPrice, double transportCosts, double totalBill) {
        this.decorId = decorId;
        this.decorName = decorName;
        this.decorQwt = decorQwt;
        this.decorPriceVAT = decorPriceVAT;
        this.totalDecorPrice = totalDecorPrice;
        this.transportCosts = transportCosts;
        this.totalBill = totalBill;
    }

    @Override
    public String toString() {
        return  decorId + ") " +  decorName + " - " + decorPriceVAT + " EUR/piece";
    }
}
