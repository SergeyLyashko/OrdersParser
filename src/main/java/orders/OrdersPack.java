package orders;

import java.util.List;

public interface OrdersPack {

    void add(List<Order> ordersList);

    List<Order> getOrdersList();
}
