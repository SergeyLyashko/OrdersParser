package orders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Pack of orders from json
 */
@Component("ordersPack")
class OrdersPackImpl implements OrdersPack {

    private final List<Order> pack = new CopyOnWriteArrayList<>();

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
