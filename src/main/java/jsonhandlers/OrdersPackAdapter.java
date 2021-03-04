package jsonhandlers;

import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.util.List;

public interface OrdersPackAdapter {

    List<Order> read(JsonReader jsonReader) throws IOException;
}
