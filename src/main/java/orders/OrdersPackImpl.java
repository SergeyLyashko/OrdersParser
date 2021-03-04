package orders;

import jsonhandlers.Order;
import jsonhandlers.OrdersPack;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Pack of orders from json
 */
@Component("ordersPack")
public class OrdersPackImpl implements OrdersPack {

    private List<Order> pack;

    @Override
    public List<Order> getOrdersPack(){
        return pack;
    }

    @Override
    public void add(List<Order> ordersPack) {
        this.pack = ordersPack;
    }
}
