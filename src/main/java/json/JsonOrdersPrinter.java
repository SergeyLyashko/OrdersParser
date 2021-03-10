package json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import executors.OrdersRunnableIO;
import orders.Order;
import orders.OrdersPack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service("ordersPrinter")
class JsonOrdersPrinter implements OrdersRunnableIO {

    private static final Logger LOGGER = LogManager.getLogger(JsonOrdersPrinter.class.getName());

    private OrdersPack ordersPack;
    private CountDownLatch countDownLatch;

    @Autowired
    public void setOrdersPack(OrdersPack ordersPack){
        this.ordersPack = ordersPack;
    }

    @Override
    public void run() {
        try {
            countDownLatch.await();
            stdOutPrint();
        } catch (InterruptedException ex) {
            LOGGER.error("Thread"+Thread.currentThread().getName()+Thread.currentThread().getId()+"is interrupted", ex);
        }
    }

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
