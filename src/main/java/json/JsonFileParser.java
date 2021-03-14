package json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import main.FileParser;
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

/**
 * Parser of json files based on Gson library
 */
@Service("jsonFileParser")
@Scope("prototype")
class JsonFileParser implements FileParser, ApplicationContextAware {

    private static final Logger LOGGER = LogManager.getLogger(JsonFileParser.class.getName());
    private String fileName;
    private ApplicationContext context;
    private CountDownLatch countDownLatch;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public void setParsedFile(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        parseFile(fileName);
        countDownLatch.countDown();
    }

    /*
     * Parser of json file with generic deserializers
     */
    private void parseFile(String fileName) {
        JsonDeserializer<OrdersPack> ordersPackDeserializer = context.getBean("packDeserializer", OrdersPackDeserializer.class);
        OrderBuilderDeserializer orderBuilderDeserializer = context.getBean("builderDeserializer", OrderBuilderDeserializer.class);
        orderBuilderDeserializer.setFile(fileName);
        GsonBuilder gsonBuilder = context.getBean("gsonBuilder", GsonBuilder.class);
        Gson gson = gsonBuilder
                .registerTypeAdapter(OrderBuilder.class, orderBuilderDeserializer)
                .registerTypeAdapter(OrdersPack.class, ordersPackDeserializer)
                .create();
        BufferedReader reader = readFile(fileName);
        if(reader != null) {
            gson.fromJson(reader, OrdersPack.class);
        }
    }

    /*
     * NIO file buffered reader
     */
    private BufferedReader readFile(String fileName) {
        try {
            return Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            LOGGER.error("File "+fileName+" was not found and will not be included for parsing.");
        }
        return null;
    }
}
