package jsonparser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import main.OrdersParser;
import orders.Order;
import orders.OrdersPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service("jsonOrdersParser")
public class JsonOrdersParser implements Runnable, OrdersParser {

    private OrdersPackAdapter ordersPackAdapter;
    private JsonDeserializer<OrdersPack> jsonDeserializer;
    private String fileName;

    @Autowired
    public void setOrdersPackAdapter(OrdersPackAdapter ordersPackAdapter){
        this.ordersPackAdapter = ordersPackAdapter;
    }

    @Autowired
    public void setJsonDeserializer(JsonDeserializer<OrdersPack> jsonDeserializer){
        this.jsonDeserializer = jsonDeserializer;
    }

    @Override
    public void run() {
        // TODO TEST
        System.out.println(Thread.currentThread().getName());
        parse(fileName);
    }

    private void parse(String fileName) {
        ordersPackAdapter.setFileName(fileName);
        Type orderListType = new TypeToken<List<Order>>() {}.getType();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(OrdersPack.class, jsonDeserializer)
                .registerTypeAdapter(orderListType, ordersPackAdapter)
                .create();
        BufferedReader reader = reader(fileName);
        if(reader != null) {
            gson.fromJson(reader, OrdersPack.class);
        }
    }

    @Override
    public void setFile(String fileName) {
        this.fileName = fileName;
    }

    private BufferedReader reader(String fileName) {
        try {
            return Files.newBufferedReader(Paths.get(fileName), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("File "+fileName+" not found and will not be included for parsing.");
        }
        return null;
    }
}
