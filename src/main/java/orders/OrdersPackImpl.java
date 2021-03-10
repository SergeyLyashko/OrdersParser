package orders;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Pack of orders
 */
@Component("ordersPack")
class OrdersPackImpl implements OrdersPack {

    // Concurrency orders packaging
    private final List<Order> pack;

    OrdersPackImpl(){
        pack = new CopyOnWriteArrayList<>();
    }

    @Override
    public List<Order> getOrdersList() {
        return pack;
    }

    @Override
    public void addOrder(Order order) {
        pack.add(order);
    }
}
