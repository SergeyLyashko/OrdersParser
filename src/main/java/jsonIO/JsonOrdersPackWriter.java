package jsonIO;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import executors.OrdersIO;
import orders.Order;
import orders.OrdersPack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service("ordersWriter")
class JsonOrdersPackWriter implements OrdersIO/*, ApplicationContextAware*/ {

    private static final Logger LOGGER = LogManager.getLogger(JsonOrdersPackWriter.class.getName());

    // TODO ????/
    //private JsonSerializer<OrdersPack> jsonSerializer;
    private OrdersPack ordersPack;
    //private ApplicationContext context;
    private CountDownLatch countDownLatch;
    /*
    @Autowired
    public void setJsonSerializer(JsonSerializer<OrdersPack> jsonSerializer){
        this.jsonSerializer = jsonSerializer;
    }*/

    @Autowired
    public void setOrdersPack(OrdersPack ordersPack){
        this.ordersPack = ordersPack;
    }
    /*
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }*/

    @Override
    public void run() {
        try {
            countDownLatch.await();
            stdOutPrint();
        } catch (InterruptedException ex) {
            LOGGER.error("Thread"+Thread.currentThread().getName()+Thread.currentThread().getId()+"is interrupted", ex);
        }
    }

    /*
    private Gson buildJson(){
        OrdersPackAdapter ordersPackAdapter = context.getBean("ordersPackAdapter", OrdersPackAdapter.class);
        Type orderListType = new TypeToken<List<Order>>() {}.getType();
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(OrdersPack.class, jsonSerializer)
                .registerTypeAdapter(orderListType, ordersPackAdapter)
                .create();
    }*/

    private void stdOutPrint(){
        Type orderListType = new TypeToken<List<Order>>() {}.getType();
        JsonElement element = new Gson().toJsonTree(ordersPack.getOrdersList(), orderListType);
        element.getAsJsonArray().forEach(System.out::println);
    }

    @Override
    public void setFile(String fileName) {
        // set out file for write
    }

    @Override
    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }
}
