package csv;

import com.opencsv.CSVParser;
import orders.OrderBuilder;
import main.FileParser;
import orders.OrdersPack;
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

@Service("csvFileParser")
@Scope("prototype")
class CsvFileParser implements FileParser, ApplicationContextAware {

    private static final Logger LOGGER = LogManager.getLogger(CsvFileParser.class.getName());

    private ApplicationContext context;
    private CSVParser csvParser;
    private String fileName;
    private CountDownLatch countDownLatch;
    private OrdersPack ordersPack;

    @Autowired
    public void setCsvParser(CSVParser csvParser){
        this.csvParser = csvParser;
    }

    @Autowired
    public void setOrdersPack(OrdersPack ordersPack){
        this.ordersPack = ordersPack;
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
        parseFile(fileName);
        countDownLatch.countDown();
    }

    private void parseFile(String fileName) {
        try(Stream<String> lines = Files.lines(Paths.get(fileName), StandardCharsets.UTF_8)){
            AtomicInteger index = new AtomicInteger(1);
            lines.skip(1).forEach(line -> parseLine(line, fileName, index.getAndIncrement()));
        } catch (IOException ex) {
            LOGGER.error("File "+fileName+" was not found and will not be included for parsing.");
        }
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
            ordersPack.addOrder(orderBuilder.buildOrder());
        } catch (IOException ex) {
            LOGGER.error("line not parsed in order from file: "+fileName);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
