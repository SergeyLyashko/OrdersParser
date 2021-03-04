package jsons;

import handlers.JsonOrder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Order from json-file
 */
@Component("jsonOrder")
@Scope("prototype")
public class JsonOrderImpl implements JsonOrder {

    private int orderId;
    private double amount;
    private String currency;
    private String comment;
    private String fileName;
    private int line;
    private String result;

    @Override
    public void setOrderId(int orderId){
        this.orderId = orderId;
    }

    @Override
    public int getOrderId(){
        return orderId;
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
    public void setCurrency(String currency){
        this.currency = currency;
    }

    @Override
    public String getCurrency(){
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
        this.fileName = fileName;
    }

    @Override
    public String getFileName(){
        return fileName;
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
