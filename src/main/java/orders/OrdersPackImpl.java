package orders;

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
    public void add(List<Order> ordersPack) {
        pack.addAll(ordersPack);
    }

    @Override
    public List<Order> getOrdersPack() {
        return pack;
    }
}
