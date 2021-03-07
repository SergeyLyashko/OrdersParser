package jsonparser;

import com.google.gson.stream.JsonReader;
import orders.Order;

import java.io.IOException;
import java.util.List;

public interface OrdersPackAdapter {

    List<Order> read(JsonReader jsonReader) throws IOException;

    void setFileName(String fileName);
}
