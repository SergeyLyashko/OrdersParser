package orders;

import java.util.Currency;

public interface Order {

    void setOrderId(int orderId);
    int getOrderId();

    void setAmount(double amount);

    void setCurrency(Currency currency);

    void setComment(String comment);
    String getComment();

    void setFileName(String fileName);
    String getFileName();

    void setLine(int line);

    void setResult(String result);
}
