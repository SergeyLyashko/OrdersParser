package orders;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class OrdersPackImplTest {

    @Test
    public void getOrdersList() {
        OrdersPackImpl ordersPackSpy = spy(new OrdersPackImpl());
        Order mockOrder = mock(Order.class);
        ordersPackSpy.addOrder(mockOrder);
        List<Order> orderList = new ArrayList<>();
        orderList.add(mockOrder);
        when(ordersPackSpy.getOrdersList()).thenReturn(orderList);
    }

    @Test
    public void addOrder() {
        Order mockOrder = mock(Order.class);
        OrdersPackImpl ordersPackSpy = spy(new OrdersPackImpl());
        ordersPackSpy.addOrder(mockOrder);
        List<Order> ordersList = ordersPackSpy.getOrdersList();
        assertEquals(ordersList.get(0), mockOrder);
    }
}