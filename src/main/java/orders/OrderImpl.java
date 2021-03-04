package orders;

import com.opencsv.bean.CsvBindByPosition;
import jsonhandlers.Order;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Order from json-file
 */
@Component("order")
@Scope("prototype")
public class OrderImpl implements Order {

    @CsvBindByPosition(position = 0)
    private int orderId;

    @CsvBindByPosition(position = 1)
    private double amount;

    private String currency;

    @CsvBindByPosition(position = 2)
    private String comment;

    // TODO перенести инициализацию ?
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
