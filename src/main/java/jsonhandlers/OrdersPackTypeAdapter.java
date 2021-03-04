package jsonhandlers;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service("ordersPackAdapter")
public class OrdersPackTypeAdapter extends TypeAdapter<List<Order>> implements OrdersPackAdapter, ApplicationContextAware {

    private ApplicationContext context;

    // TODO ????
    @Override
    public void write(JsonWriter jsonWriter, List<Order> orders) throws IOException {
        jsonWriter.beginArray();
        for(Order order: orders){
            jsonWriter.beginObject();
            jsonWriter.name("orderId").value(order.getOrderId());
            jsonWriter.name("amount").value(order.getAmount());
            jsonWriter.name("comment").value(order.getComment());
            // TODO
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
        while (jsonReader.hasNext()){
            if(jsonReader.peek().equals(JsonToken.BEGIN_OBJECT)){
                orders.add(createOrdersPack(jsonReader));
            }else{
                jsonReader.skipValue();
            }
        }
        jsonReader.endArray();
        return orders;
    }

    private Order createOrdersPack(JsonReader jsonReader) throws IOException {
        Order order = context.getBean("jsonOrder", Order.class);
        jsonReader.beginObject();
        while (jsonReader.hasNext()){
            switch (jsonReader.nextName()){
                case "orderId":
                    order.setOrderId(jsonReader.nextInt());
                    break;
                case "amount":
                    order.setAmount(jsonReader.nextDouble());
                    break;
                case "currency":
                    order.setCurrency(jsonReader.nextString());
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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
