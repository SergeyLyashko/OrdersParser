package orders;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Pack of orders from files
 */
@Component("ordersPack")
class OrdersPackImpl implements OrdersPack {

    private final List<Order> pack = new CopyOnWriteArrayList<>();

    @Override
    public void add(List<Order> ordersList) {
        pack.addAll(ordersList);
    }

    @Override
    public List<Order> getOrdersList() {
        return pack;
    }
}
