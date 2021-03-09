package jsonhandlers;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import jsonIO.OrdersPackAdapter;
import orders.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
/*
@Service("ordersPackAdapter")
@Scope("prototype")
class OrdersPackAdapterImpl extends TypeAdapter<List<Order>> implements OrdersPackAdapter, ApplicationContextAware {

    private static final Logger LOGGER = LogManager.getLogger(OrdersPackAdapterImpl.class.getName());

    private ApplicationContext context;
    private String fileName;

    @Override
    public void write(JsonWriter jsonWriter, List<Order> orders) throws IOException {
        jsonWriter.beginArray();
        for(Order order: orders){
            jsonWriter.beginObject();
            jsonWriter.name("id").value(order.getOrderId());
            jsonWriter.name("amount").value(order.getAmount());
            jsonWriter.name("comment").value(order.getComment());
            jsonWriter.name("filename").value(order.getFileName());
            jsonWriter.name("line").value(order.getLine());
            jsonWriter.name("result").value(order.getResult());
            jsonWriter.endObject();
        }
        jsonWriter.endArray();
    }

    @Override
    public List<Order> read(JsonReader jsonReader) throws IOException {
        List<Order> orders = new ArrayList<>();
        jsonReader.beginArray();
        int lineCount = 1;
        while (jsonReader.hasNext()){
            if(jsonReader.peek().equals(JsonToken.BEGIN_OBJECT)){
                Order order = createOrdersPack(jsonReader);
                order.setFileName(fileName);
                order.setLine(lineCount++);
                orders.add(order);
            }else{
                jsonReader.skipValue();
            }
        }
        jsonReader.endArray();
        return orders;
    }

    @Override
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private Order createOrdersPack(JsonReader jsonReader) throws IOException {
        Order order = context.getBean("order", Order.class);
        order.setResult("OK");
        jsonReader.beginObject();
        while (jsonReader.hasNext()){
            switch (jsonReader.nextName()){
                case "orderId":
                    order.setOrderId(jsonReader.nextInt());
                    break;
                case "amount":
                    parseAmount(order, jsonReader);
                    break;
                case "currency":
                    parseCurrency(order, jsonReader);
                    break;
                case "comment":
                    order.setComment(jsonReader.nextString());
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        jsonReader.endObject();
        return order;
    }

    private void parseAmount(Order order, JsonReader jsonReader) {
        try {
            order.setAmount(Double.parseDouble(jsonReader.nextString()));
        }catch (NumberFormatException | IOException ex){
            order.setResult("ERROR: the amount is not readable");
            LOGGER.info("amount is not readable in order id: "+order.getOrderId()+" from file: "+order.getFileName());
        }
    }

    private void parseCurrency(Order order, JsonReader jsonReader) {
        try{
            order.setCurrency(Currency.getInstance(jsonReader.nextString()));
        }catch (IllegalArgumentException | IOException ex){
            order.setResult("ERROR: the currency not defined");
            LOGGER.info("currency not defined in order id:"+order.getOrderId()+" from file: "+order.getFileName());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}

 */
