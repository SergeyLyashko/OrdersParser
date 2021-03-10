package orders;

import org.junit.Test;

import static org.mockito.Mockito.*;

public class OrderBuilderImplTest {

    @Test
    public void buildOrder() {
        OrderBuilderImpl orderBuilderMock = mock(OrderBuilderImpl.class);
        Order orderMock = mock(Order.class);
        when(orderBuilderMock.buildOrder()).thenReturn(orderMock);
    }
}