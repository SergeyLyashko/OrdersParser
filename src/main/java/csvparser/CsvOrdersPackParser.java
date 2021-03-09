package csvparser;

import com.opencsv.CSVParser;
import orders.OrderBuilder;
import orders.OrdersPack;
import executors.OrdersIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Service("csvOrdersParser")
@Scope("prototype")
class CsvOrdersPackParser implements OrdersIO, ApplicationContextAware {

    private static final Logger LOGGER = LogManager.getLogger(CsvOrdersPackParser.class.getName());

    private ApplicationContext context;
    private OrdersPack ordersPack;
    private CSVParser csvParser;
    private String fileName;
    private CountDownLatch countDownLatch;

    @Autowired
    public void setOrdersPack(OrdersPack ordersPack){
        this.ordersPack = ordersPack;
    }

    @Autowired
    public void setCsvParser(CSVParser csvParser){
        this.csvParser = csvParser;
    }

    @Override
    public void setFile(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        // TODO TEST убрать !!!
        Thread.currentThread().setName("csv-parser");
        System.out.println(Thread.currentThread().getName());
        parse(fileName);
        countDownLatch.countDown();
        System.out.println("count down: "+countDownLatch.getCount());
    }

    private void parse(String fileName) {
        readFile(fileName);
        //ordersPack.add(orders);
    }

    private void readFile(String fileName){
        //List<Order> orders = new ArrayList<>();
        try(Stream<String> lines = Files.lines(Paths.get(fileName), StandardCharsets.UTF_8)){
            AtomicInteger index = new AtomicInteger(1);
            lines.skip(1).forEach(line -> {
                parseLine(line, fileName, index.getAndIncrement());
                /*
                Order order = context.getBean("order", Order.class);
                order.setResult("OK");
                order.setFileName(fileName);
                order.setLine(index.getAndIncrement());
                parseLine(order, line);
                orders.add(order);*/
            });
        } catch (IOException ex) {
            LOGGER.error("File "+fileName+" was not found and will not be included for parsing.");
            // TODO убрать
            //System.err.println("File "+fileName+" was not found and will not be included for parsing.");
        }
        //return orders;
    }

    private void parseLine(String line, String fileName, int lineIndex){
        try {
            String[] parseLine = csvParser.parseLine(line);
            OrderBuilder orderBuilder = context.getBean("orderBuilder", OrderBuilder.class);
            orderBuilder.setOrderId(parseLine[0]);
            orderBuilder.setAmount(parseLine[1]);
            orderBuilder.setCurrency(parseLine[2]);
            orderBuilder.setComment(parseLine[3]);
            orderBuilder.setFileName(fileName);
            orderBuilder.setLineIndex(lineIndex);
            orderBuilder.buildOrder();
            /*
            order.setOrderId(Integer.parseInt(parseLine[0]));
            parseAmount(order, parseLine[1]);
            parseCurrency(order, parseLine[2]);
            order.setComment(parseLine[3]);*/
        } catch (IOException ex) {
            // TODO !
            //LOGGER.error("line not parsed in order id: "+order.getOrderId()+" from file: "+order.getFileName());
        }
    }
    /*
    private void parseLine(Order order, String line){
        try {
            String[] parseLine = csvParser.parseLine(line);
            order.setOrderId(Integer.parseInt(parseLine[0]));
            parseAmount(order, parseLine[1]);
            parseCurrency(order, parseLine[2]);
            order.setComment(parseLine[3]);
        } catch (IOException ex) {
            LOGGER.error("line not parsed in order id: "+order.getOrderId()+" from file: "+order.getFileName());
        }
    }

    private void parseAmount(Order order, String cell) {
        try {
            order.setAmount(Double.parseDouble(cell));
        }catch (NumberFormatException ex){
            order.setResult("ERROR: the amount is not readable");
            LOGGER.info("amount is not readable in order id: "+order.getOrderId()+" from file: "+order.getFileName());
        }
    }

    private void parseCurrency(Order order, String cell) {
        try{
            order.setCurrency(Currency.getInstance(cell));
        }catch (IllegalArgumentException ex){
            order.setResult("ERROR: the currency not defined");
            LOGGER.info("currency not defined in order id:"+order.getOrderId()+" from file: "+order.getFileName());
        }
    }*/

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
