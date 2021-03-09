package jsonhandlers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import orders.OrdersPack;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
/*
@Service("jsonSerializer")
class OrdersPackSerializerWithTypeAdapter implements JsonSerializer<OrdersPack> {

    @Override
    public JsonElement serialize(OrdersPack ordersPack, Type type, JsonSerializationContext context) {
        JsonObject newJsonObject = new JsonObject();
        Type ordersListType = new TypeToken<OrdersPack>() {}.getType();
        newJsonObject.add("orders", context.serialize(ordersPack.getOrdersList(), ordersListType));
        return newJsonObject;
    }
}


 */