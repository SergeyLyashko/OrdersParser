package jsonhandlers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import orders.Order;
import orders.OrderBuilder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("builderList")
public class BuilderListDeserializer implements JsonDeserializer<List<OrderBuilder>> {

    @Override
    public List<OrderBuilder> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Type ordersListType = new TypeToken<List<OrderBuilder>>() {}.getType();
        return context.deserialize(jsonObject.getAsJsonArray("orders"), ordersListType);

        /*
        List<OrderBuilder> builderList = new ArrayList<>();
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        for(Map.Entry<String, JsonElement> entry : jsonObject.entrySet()){
            OrderBuilder orderBuilder = context.deserialize(entry.getValue(), OrderBuilder.class);
            builderList.add(orderBuilder);
        }
        return builderList;
        */
    }

}
