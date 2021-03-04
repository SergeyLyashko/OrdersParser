package handlers;

import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.util.List;

public interface OrdersPackAdapter {

    List<JsonOrder> read(JsonReader jsonReader) throws IOException;
}
