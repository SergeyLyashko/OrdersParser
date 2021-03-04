package jsons;

import handlers.JsonOrder;
import handlers.OrdersPack;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Pack of orders from json
 */
@Component("ordersPack")
public class OrdersPackImpl implements OrdersPack {

    private List<JsonOrder> pack;

    @Override
    public List<JsonOrder> getOrdersPack(){
        return pack;
    }

    @Override
    public void add(List<JsonOrder> ordersPack) {
        this.pack = ordersPack;
    }
}
