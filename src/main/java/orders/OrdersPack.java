package orders;

import java.util.List;

public interface OrdersPack {

    List<Order> getOrdersList();

    void addOrder(Order order);
}
