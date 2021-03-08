package jsonIO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import executors.OrdersIO;
import orders.Order;
import orders.OrdersPack;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service("jsonOrdersParser")
@Scope("prototype")
class JsonOrdersPackParser implements OrdersIO, ApplicationContextAware {

    private JsonDeserializer<OrdersPack> jsonDeserializer;
    private String fileName;
    private ApplicationContext context;
    private CountDownLatch countDownLatch;

    @Autowired
    public void setJsonDeserializer(JsonDeserializer<OrdersPack> jsonDeserializer){
        this.jsonDeserializer = jsonDeserializer;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public void setFile(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        // TODO TEST
        Thread.currentThread().setName("json-parser");
        System.out.println(Thread.currentThread().getName());
        parse(fileName);
        countDownLatch.countDown();
        System.out.println("count down: "+countDownLatch.getCount());
    }

    private void parse(String fileName) {
        OrdersPackAdapter ordersPackAdapter = context.getBean("ordersPackAdapter", OrdersPackAdapter.class);
        ordersPackAdapter.setFileName(fileName);
        Type orderListType = new TypeToken<List<Order>>() {}.getType();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(OrdersPack.class, jsonDeserializer)
                .registerTypeAdapter(orderListType, ordersPackAdapter)
                .create();
        BufferedReader reader = readFile(fileName);
        if(reader != null) {
            gson.fromJson(reader, OrdersPack.class);
        }
    }

    private BufferedReader readFile(String fileName) {
        try {
            return Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("File "+fileName+" not found and will not be included for parsing.");
        }
        return null;
    }
}
