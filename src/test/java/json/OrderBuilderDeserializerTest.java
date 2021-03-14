package json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import orders.OrderBuilder;
import org.junit.Test;

import java.lang.reflect.Type;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderBuilderDeserializerTest {

    @Test
    public void deserialize() {
        OrderBuilderDeserializer deserializerMock = mock(OrderBuilderDeserializer.class);
        JsonElement jsonElementMock = mock(JsonElement.class);
        Type typeMock = mock(Type.class);
        JsonDeserializationContext contextMock = mock(JsonDeserializationContext.class);
        OrderBuilder orderBuilderMock = mock(OrderBuilder.class);
        when(deserializerMock.deserialize(jsonElementMock, typeMock, contextMock)).thenReturn(orderBuilderMock);
    }


}