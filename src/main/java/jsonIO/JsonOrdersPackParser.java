package jsonIO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import executors.OrdersIO;
import jsonhandlers.OrderBuilderDeserializer;
import jsonhandlers.OrdersPackDeserializer;
import orders.OrderBuilder;
import orders.OrdersPack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;

@Service("jsonOrdersParser")
@Scope("prototype")
class JsonOrdersPackParser implements OrdersIO, ApplicationContextAware {

    private static final Logger LOGGER = LogManager.getLogger(JsonOrdersPackParser.class.getName());

    private String fileName;
    private ApplicationContext context;
    private CountDownLatch countDownLatch;

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
        parse(fileName);
        countDownLatch.countDown();
    }

    private void parse(String fileName) {
        OrdersPackDeserializer ordersPackDeserializer = context.getBean("packDeserializer", OrdersPackDeserializer.class);
        OrderBuilderDeserializer orderBuilderDeserializer = context.getBean("orderBuilderDeserializer", OrderBuilderDeserializer.class);
        orderBuilderDeserializer.setFile(fileName);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(OrderBuilder.class, orderBuilderDeserializer)
                .registerTypeAdapter(OrdersPack.class, ordersPackDeserializer)
                .create();
        BufferedReader reader = readFile(fileName);
        if(reader != null) {
            gson.fromJson(reader, OrdersPack.class);
        }
    }

    private BufferedReader readFile(String fileName) {
        try {
            return Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            LOGGER.error("File "+fileName+" was not found and will not be included for parsing.");
        }
        return null;
    }
}
