package handlers;

import java.util.List;

public interface OrdersPack {

    void add(List<Order> ordersPack);

    List<Order> getOrdersPack();

    void print();
}
