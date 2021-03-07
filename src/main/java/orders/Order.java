package orders;

import java.util.Currency;

public interface Order {

    void setOrderId(int orderId);
    int getOrderId();

    void setAmount(double amount);
    double getAmount();

    void setCurrency(Currency currency);
    Currency getCurrency();

    void setComment(String comment);
    String getComment();

    void setFileName(String fileName);
    String getFileName();

    void setLine(int line);
    int getLine();

    void setResult(String result);
    String getResult();
}
