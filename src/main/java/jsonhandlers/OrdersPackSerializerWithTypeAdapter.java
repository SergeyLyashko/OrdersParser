package jsonhandlers;

/*
import java.lang.reflect.Type;
// TODO ????? DEL ??
@Service("jsonSerializer")
public class OrdersPackSerializerWithTypeAdapter implements JsonSerializer<OrdersPack> {

    @Override
    public JsonElement serialize(OrdersPack ordersPack, Type type, JsonSerializationContext context) {
        JsonObject newJsonObject = new JsonObject();
        Type ordersListType = new TypeToken<OrdersPack>() {}.getType();
        newJsonObject.add("orders", context.serialize(ordersPack.getOrdersPack(), ordersListType));
        return newJsonObject;
    }
}
*/