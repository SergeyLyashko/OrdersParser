package json;

import com.google.gson.*;
import orders.OrderBuilderImpl;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;

@Service("orderBuilderDeserializer")
@Scope("prototype")
public class OrderBuilderDeserializer implements JsonDeserializer<OrderBuilderImpl>, ApplicationContextAware {

    private ApplicationContext applicationContext;
    private String fileName;

    public void setFile(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public OrderBuilderImpl deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        OrderBuilderImpl orderBuilder = applicationContext.getBean("orderBuilder", OrderBuilderImpl.class);
        orderBuilder.setFileName(fileName);
        orderBuilder.setOrderId(jsonObject.get("orderId").getAsString());
        orderBuilder.setAmount(jsonObject.get("amount").getAsString());
        orderBuilder.setCurrency(jsonObject.get("currency").getAsString());
        orderBuilder.setComment(jsonObject.get("comment").getAsString());
        return orderBuilder;
    }
}
