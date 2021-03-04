package handlers;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service("ordersPackAdapter")
public class OrdersPackTypeAdapter extends TypeAdapter<List<JsonOrder>> implements OrdersPackAdapter, ApplicationContextAware {

    private ApplicationContext context;
    private OrdersPack ordersPack;

    @Autowired
    public void setOrdersPack(OrdersPack ordersPack){
        this.ordersPack = ordersPack;
    }

    @Override
    public void write(JsonWriter jsonWriter, List<JsonOrder> orders) throws IOException {
        jsonWriter.beginArray();
        for(JsonOrder order: orders){
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
    public List<JsonOrder> read(JsonReader jsonReader) throws IOException {
        List<JsonOrder> orders = new ArrayList<>();
        jsonReader.beginArray();
        while (jsonReader.hasNext()){
            if(jsonReader.peek().equals(JsonToken.BEGIN_OBJECT)){
                orders.add(createPack(jsonReader));
            }else{
                jsonReader.skipValue();
            }
        }
        jsonReader.endArray();
        return orders;
    }

    private JsonOrder createPack(JsonReader jsonReader) throws IOException {
        JsonOrder jsonOrder = context.getBean("jsonOrder", JsonOrder.class);
        jsonReader.beginObject();
        while (jsonReader.hasNext()){
            switch (jsonReader.nextName()){
                case "orderId":
                    jsonOrder.setOrderId(jsonReader.nextInt());
                    break;
                case "amount":
                    jsonOrder.setAmount(jsonReader.nextDouble());
                    break;
                case "currency":
                    jsonOrder.setCurrency(jsonReader.nextString());
                    break;
                case "comment":
                    jsonOrder.setComment(jsonReader.nextString());
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        jsonReader.endObject();
        return jsonOrder;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
