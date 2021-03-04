package handlers;

import java.util.List;

public interface OrdersPack {

    void add(List<JsonOrder> ordersPack);

    List<JsonOrder> getOrdersPack();
}
