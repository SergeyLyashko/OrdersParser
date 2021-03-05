package orders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import handlers.Order;
import handlers.OrdersPack;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Pack of orders from json
 */
@Component("ordersPack")
public class OrdersPackImpl implements OrdersPack {

    private final List<Order> pack = new ArrayList<>();

    @Override
    public List<Order> getOrdersPack(){
        return pack;
    }

    @Override
    public void print(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(this));
    }

    @Override
    public void add(List<Order> ordersPack) {
        pack.addAll(ordersPack);
    }
}
