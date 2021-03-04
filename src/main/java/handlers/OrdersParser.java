package handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service("ordersParser")
public class OrdersParser {

    // TODO test
    private String filePath = "orders.json";
    private OrdersPack ordersPack;
    private OrdersPackAdapter ordersPackAdapter;
    private JsonDeserializer<OrdersPack> jsonDeserializer;

    @Autowired
    public void setOrdersPack(OrdersPack ordersPack){
        this.ordersPack = ordersPack;
    }

    @Autowired
    public void setOrdersPackAdapter(OrdersPackAdapter ordersPackAdapter){
        this.ordersPackAdapter = ordersPackAdapter;
    }

    @Autowired
    public void setJsonDeserializer(JsonDeserializer<OrdersPack> jsonDeserializer){
        this.jsonDeserializer = jsonDeserializer;
    }

    public OrdersPack getOrdersPack(){
        return ordersPack;
    }

    public void parse() {
        Type orderListType = new TypeToken<List<JsonOrder>>() {}.getType();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(OrdersPack.class, jsonDeserializer)
                .registerTypeAdapter(orderListType, ordersPackAdapter)
                .create();
        InputStreamReader inputStreamReader = encodeReader(filePath);
        if(inputStreamReader != null) {
            this.ordersPack = gson.fromJson(inputStreamReader, OrdersPack.class);
        }
    }

    // TODO test method
    public void print(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(ordersPack));
    }

    // TODO переписать на NIO
    private InputStreamReader encodeReader(String filePath) {
        try {
            return new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
