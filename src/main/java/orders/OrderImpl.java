package orders;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Currency;

/**
 * Order from json-file
 */
@Component("order")
@Scope("prototype")
class OrderImpl implements Order {

    private int id;
    private int line;
    private double amount;
    private Currency currency;
    private String comment;
    private String filename;
    private String result;

    public OrderImpl(){}

    @Override
    public void setOrderId(int orderId){
        this.id = orderId;
    }

    @Override
    public int getOrderId(){
        return id;
    }

    @Override
    public void setAmount(double amount){
        this.amount = amount;
    }

    @Override
    public double getAmount(){
        return amount;
    }

    @Override
    public void setCurrency(Currency currency){
        this.currency = currency;
    }

    @Override
    public Currency getCurrency(){
        return currency;
    }

    @Override
    public void setComment(String comment){
        this.comment = comment;
    }

    @Override
    public String getComment(){
        return comment;
    }

    @Override
    public void setFileName(String fileName){
        this.filename = fileName;
    }

    @Override
    public String getFileName(){
        return filename;
    }

    @Override
    public void setLine(int line){
        this.line = line;
    }

    @Override
    public int getLine(){
        return line;
    }

    @Override
    public void setResult(String result){
        this.result = result;
    }

    @Override
    public String getResult(){
        return result;
    }
}