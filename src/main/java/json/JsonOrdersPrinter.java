package json;

import com.google.gson.Gson;
import orders.OrdersPack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.CountDownLatch;

@Service("ordersPrinter")
class JsonOrdersPrinter implements OrdersPrinter {

    private static final Logger LOGGER = LogManager.getLogger(JsonOrdersPrinter.class.getName());
    private OrdersPack ordersPack;
    private CountDownLatch countDownLatch;
    private Gson gson;

    @Autowired
    public void setOrdersPack(OrdersPack ordersPack){
        this.ordersPack = ordersPack;
    }

    @Autowired
    public void setGson(Gson gson){
        this.gson = gson;
    }

    @Override
    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
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
        gson.toJsonTree(ordersPack.getOrdersList())
                .getAsJsonArray()
                .forEach(System.out::println);
    }
}
