package parsers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import handlers.Order;
import handlers.OrdersPack;
import handlers.OrdersParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service("jsonParser")
public class JsonOrdersParser implements OrdersParser {

    private OrdersPackAdapter ordersPackAdapter;
    private JsonDeserializer<OrdersPack> jsonDeserializer;

    @Autowired
    public void setOrdersPackAdapter(OrdersPackAdapter ordersPackAdapter){
        this.ordersPackAdapter = ordersPackAdapter;
    }

    @Autowired
    public void setJsonDeserializer(JsonDeserializer<OrdersPack> jsonDeserializer){
        this.jsonDeserializer = jsonDeserializer;
    }

    @Override
    public void parse(String fileName) {
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

    private BufferedReader reader(String filePath) {
        try {
            return Files.newBufferedReader(Paths.get(filePath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
