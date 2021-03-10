package orders;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Currency;

/**
 * This order is one parse unit of file
 */
@Component("order")
@Scope("prototype")
class Order {

    private int id;
    private int line;
    private double amount;
    private Currency currency;
    private String comment;
    private String filename;
    private String result;

    public Order(){}

    public void setOrderId(int orderId){
        this.id = orderId;
    }

    public void setLine(int line){
        this.line = line;
    }

    public void setResult(String result){
        this.result = result;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public void setCurrency(Currency currency){
        this.currency = currency;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public void setFileName(String fileName){
        this.filename = fileName;
    }

    public int getOrderId(){
        return id;
    }

    public String getFileName(){
        return filename;
    }
}