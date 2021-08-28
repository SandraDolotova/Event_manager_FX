package decor;

import lombok.Getter;

@Getter
public class Decor {
    int decorId;
    String decorName;
    int decorQwt;
    double decorPrice;
    double decorPriceVAT;
    String decorStatus;

    public Decor(){}


    public Decor(int decorId, String decorName, int decorQwt, double decorPrice, double decorPriceVAT, String decorStatus) {
        this.decorId = decorId;
        this.decorName = decorName;
        this.decorQwt = decorQwt;
        this.decorPrice = decorPrice;
        this.decorPriceVAT = decorPriceVAT;
        this.decorStatus = decorStatus;
    }

    public Decor(int decorId, String decorName, int decorQwt, double decorPriceVAT) {
        this.decorId = decorId;
        this.decorName = decorName;
        this.decorQwt = decorQwt;
        this.decorPriceVAT = decorPriceVAT;
    }

    public Decor(int decorId, String decorName, double decorPriceVAT) {
        this.decorId = decorId;
        this.decorName = decorName;
        this.decorPriceVAT = decorPriceVAT;
    }

    @Override
    public String toString() {
        return  decorId + ") " +  decorName + " - " + decorPriceVAT + "EUR/peace";
    }
}
