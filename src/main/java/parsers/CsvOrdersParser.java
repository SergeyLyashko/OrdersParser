package parsers;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import handlers.Order;
import handlers.OrdersPack;
import handlers.OrdersParser;
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
public class CsvOrdersParser implements OrdersParser {

    private OrdersPack ordersPack;

    @Autowired
    public void setOrdersPack(OrdersPack ordersPack){
        this.ordersPack = ordersPack;
    }

    @Override
    public void parse(String fileName) {
        BufferedReader reader = reader(fileName);
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
