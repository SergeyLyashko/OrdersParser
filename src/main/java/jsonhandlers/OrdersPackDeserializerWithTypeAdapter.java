package jsonhandlers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import orders.Order;
import orders.OrdersPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
/*
@Service("jsonDeserializer")
class OrdersPackDeserializerWithTypeAdapter implements JsonDeserializer<OrdersPack> {

    private OrdersPack ordersPack;

    @Autowired
    public void setOrdersPack(OrdersPack ordersPack){
        this.ordersPack = ordersPack;
    }

    @Override
    public OrdersPack deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Type ordersListType = new TypeToken<List<Order>>() {}.getType();
        List<Order> orders = context.deserialize(jsonObject.getAsJsonArray("orders"), ordersListType);
        ordersPack.add(orders);
        return ordersPack;
    }
}

 */
