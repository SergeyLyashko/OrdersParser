package jsonhandlers;

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
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        AtomicInteger index = new AtomicInteger(1);
        for(Map.Entry<String, JsonElement> entry : jsonObject.entrySet()){
            if(entry.getValue().isJsonArray()) {
                entry.getValue().getAsJsonArray().forEach(element -> {
                    OrderBuilder orderBuilder = context.deserialize(element, OrderBuilder.class);
                    orderBuilder.setLineIndex(index.getAndIncrement());
                    Order order = orderBuilder.buildOrder();
                    ordersPack.addOrder(order);
                });
            }
        }
        return ordersPack;
    }
}
