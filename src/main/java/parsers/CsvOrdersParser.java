package parsers;

import com.opencsv.CSVParser;
import handlers.Order;
import handlers.OrdersPack;
import handlers.OrdersParser;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Service("csvOrdersParser")
public class CsvOrdersParser implements OrdersParser, ApplicationContextAware {

    private ApplicationContext context;
    private OrdersPack ordersPack;
    private CSVParser csvParser;

    @Autowired
    public void setOrdersPack(OrdersPack ordersPack){
        this.ordersPack = ordersPack;
    }

    @Autowired
    public void setCsvParser(CSVParser csvParser){
        this.csvParser = csvParser;
    }

    @Override
    public void parse(String fileName) {
        List<Order> orders = readFile(fileName);
        ordersPack.add(orders);
    }

    private List<Order> readFile(String fileName){
        List<Order> orders = new ArrayList<>();
        try(Stream<String> lines = Files.lines(Paths.get(fileName), StandardCharsets.UTF_8)){
            AtomicInteger index = new AtomicInteger(1);
            lines.skip(1).forEach(line -> {
                Order order = context.getBean("order", Order.class);
                order.setResult("OK");
                order.setFileName(fileName);
                order.setLine(index.getAndIncrement());
                parseLine(order, line);
                orders.add(order);
            });
        } catch (IOException e) {
            System.err.println("File "+fileName+" not found and will not be included for parsing.");
        }
        return orders;
    }

    private void parseLine(Order order, String line){
        try {
            String[] parseLine = csvParser.parseLine(line);
            order.setOrderId(Integer.parseInt(parseLine[0]));
            parseAmount(order, parseLine[1]);
            parseCurrency(order, parseLine[2]);
            order.setComment(parseLine[3]);
        } catch (IOException e) {
            order.setResult("ERROR: line not parsed");
        }
    }

    private void parseAmount(Order order, String cell) {
        try {
            order.setAmount(Double.parseDouble(cell));
        }catch (NumberFormatException ex){
            order.setResult("ERROR: the amount is not readable");
        }
    }

    private void parseCurrency(Order order, String cell) {
        try{
            order.setCurrency(Currency.getInstance(cell));
        }catch (IllegalArgumentException ex){
            order.setResult("ERROR: the currency not defined");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
