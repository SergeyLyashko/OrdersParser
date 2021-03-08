package jsonwriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import jsonparser.OrdersPackAdapter;
import orders.Order;
import orders.OrdersPack;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service("jsonOrderWriter")
public class JsonOrderWriter implements Runnable, ApplicationContextAware {

    private JsonSerializer<OrdersPack> jsonSerializer;
    private OrdersPack ordersPack;
    private ApplicationContext context;

    @Autowired
    public void setJsonSerializer(JsonSerializer<OrdersPack> jsonSerializer){
        this.jsonSerializer = jsonSerializer;
    }

    @Autowired
    public void setOrdersPack(OrdersPack ordersPack){
        this.ordersPack = ordersPack;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("writer");
        System.out.println(Thread.currentThread().getName());
        print();
    }

    private void print(){
        OrdersPackAdapter ordersPackAdapter = context.getBean("ordersPackAdapter", OrdersPackAdapter.class);
        Type orderListType = new TypeToken<List<Order>>() {}.getType();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(OrdersPack.class, jsonSerializer)
                .registerTypeAdapter(orderListType, ordersPackAdapter)
                .create();
        System.out.println(gson.toJson(ordersPack));
    }
}
