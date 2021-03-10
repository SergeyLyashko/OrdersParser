package orders;

public interface OrderBuilder {

    void setOrderId(String orderId);

    void setAmount(String amount);

    void setCurrency(String currency);

    void setComment(String comment);

    void setFileName(String fileName);

    void setLineIndex(int lineIndex);

    void buildOrder();
}
