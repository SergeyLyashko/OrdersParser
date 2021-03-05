package jsonhandlers;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import orders.OrderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service("csvParser")
public class CsvParser {

    private OrdersPack ordersPack;
    // TODO test
    private String filePath = "orders.csv";

    @Autowired
    public void setOrdersPack(OrdersPack ordersPack){
        this.ordersPack = ordersPack;
    }

    public void parse() {
        BufferedReader reader = reader(filePath);
        if(reader != null){
            CSVReader csvReader = new CSVReader(reader);
            List<Order> orders = buildOrdersList(csvReader);
            ordersPack.add(orders);
        }
    }

    private List<Order> buildOrdersList(CSVReader csvReader) {
        CsvToBeanBuilder<Order> beanBuilder = new CsvToBeanBuilder<>(csvReader);
        return beanBuilder.withType(OrderImpl.class).build().parse();
    }

    private BufferedReader reader(String filePath) {
        try {
            return Files.newBufferedReader(Paths.get(filePath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
