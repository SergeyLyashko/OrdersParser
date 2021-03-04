package jsonhandlers;

public interface Order {

    void setOrderId(int orderId);
    int getOrderId();

    void setAmount(double amount);
    double getAmount();

    void setCurrency(String currency);
    String getCurrency();

    void setComment(String comment);
    String getComment();

    void setFileName(String fileName);
    String getFileName();

    void setLine(int line);
    int getLine();

    void setResult(String result);
    String getResult();
}
