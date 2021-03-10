package json;

import com.google.gson.*;
import orders.Order;
import orders.OrderBuilder;
import orders.OrdersPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service("packDeserializer")
public class OrdersPackDeserializer implements JsonDeserializer<OrdersPack> {

    private OrdersPack ordersPack;

    @Autowired
    public void setOrdersPack(OrdersPack ordersPack){
        this.ordersPack = ordersPack;
    }

    @Override
    public OrdersPack deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        if(jsonElement.isJsonArray()){
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            readAsArray(jsonArray, context);
        }
        else if(jsonElement.isJsonObject()){
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            readAsObject(jsonObject, context);
        }
        return ordersPack;
    }

    private void readAsArray(JsonArray jsonArray, JsonDeserializationContext context) {
        AtomicInteger index = new AtomicInteger(1);
        jsonArray.forEach(element -> readAsPrimitive(element, context, index.getAndIncrement()));
    }

    private void readAsObject(JsonObject jsonObject, JsonDeserializationContext context){
        for(Map.Entry<String, JsonElement> entry : jsonObject.entrySet()){
            if(entry.getValue().isJsonArray()) {
                JsonArray jsonArray = entry.getValue().getAsJsonArray();
                readAsArray(jsonArray, context);
            }
            else if(entry.getValue().isJsonPrimitive()){
                readAsPrimitive(jsonObject, context, 1);
                return;
            }
        }
    }

    private void readAsPrimitive(JsonElement jsonElement, JsonDeserializationContext context, int lineIndex) {
        OrderBuilder orderBuilder = context.deserialize(jsonElement, OrderBuilder.class);
        orderBuilder.setLineIndex(lineIndex);
        Order order = orderBuilder.buildOrder();
        ordersPack.addOrder(order);
    }
}
