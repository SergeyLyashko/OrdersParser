package json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import orders.OrdersPack;
import org.junit.Test;

import java.lang.reflect.Type;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrdersPackDeserializerTest {

    @Test
    public void deserialize() {
        OrdersPackDeserializer deserializerMock = mock(OrdersPackDeserializer.class);
        JsonElement jsonElementMock = mock(JsonElement.class);
        Type typeMock = mock(Type.class);
        JsonDeserializationContext contextMock = mock(JsonDeserializationContext.class);
        OrdersPack ordersPackMock = mock(OrdersPack.class);
        when(deserializerMock.deserialize(jsonElementMock, typeMock, contextMock)).thenReturn(ordersPackMock);
    }
}