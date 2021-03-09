package jsonhandlers;

import com.google.gson.*;
import orders.OrderBuilder;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;

@Service("orderBuilderDeserializer")
public class OrderBuilderDeserializer implements JsonDeserializer<OrderBuilder>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public OrderBuilder deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        OrderBuilder orderBuilder = applicationContext.getBean("orderBuilder", OrderBuilder.class);
        orderBuilder.setOrderId(jsonObject.get("orderId").getAsString());
        orderBuilder.setAmount(jsonObject.get("amount").getAsString());
        orderBuilder.setCurrency(jsonObject.get("currency").getAsString());
        orderBuilder.setComment(jsonObject.get("comment").getAsString());
        return orderBuilder;
    }
}
