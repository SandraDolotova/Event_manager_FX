package payment;

import lombok.Getter;

@Getter
public class Bill {
    int billId;
    int customerId;
    String eventName;
    double totalBill;
    boolean paymentStatus;


    public Bill(int billId, int customerId, String eventName, double totalBill, boolean paymentStatus) {
        this.billId = billId;
        this.customerId = customerId;
        this.eventName = eventName;
        this.totalBill = totalBill;
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return billId + customerId + eventName + totalBill + paymentStatus;
    }
}
