package orders;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvNumber;
import handlers.Order;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Currency;

/**
 * Order from json-file
 */
@Component("order")
@Scope("prototype")
public class OrderImpl implements Order {

    public OrderImpl(){}

    @CsvBindByName
    private int id;

    @CsvBindByName
    @CsvNumber(value = "#,##")
    private double amount;

    @CsvBindByName
    private Currency currency;

    @CsvBindByName
    private String comment;

    private String filename;
    private int line;
    private String result;

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