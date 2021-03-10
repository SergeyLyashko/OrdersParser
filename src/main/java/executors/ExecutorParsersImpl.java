package executors;

import main.ExecutorParsers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("executorParsers")
class ExecutorParsersImpl implements ExecutorParsers {

    private static final Logger LOGGER = LogManager.getLogger(ExecutorParsersImpl.class.getName());

    // защелка
    private CountDownLatch countDownLatch;
    private final ExecutorService writer;
    private final ExecutorService parseService;
    private final Queue<OrdersRunnableIO> parsersPool;
    private OrdersRunnableIO ordersPrinter;
    private FileParser fileParser;

    public ExecutorParsersImpl(){
        parsersPool = new ConcurrentLinkedQueue<>();
        writer = Executors.newSingleThreadExecutor();
        parseService = Executors.newCachedThreadPool();
    }

    @Autowired
    public void setFileParser(FileParser fileParser){
        this.fileParser = fileParser;
    }

    @Autowired
    @Qualifier("ordersPrinter")
    public void setOrdersPrinter(OrdersRunnableIO ordersPrinter){
        this.ordersPrinter = ordersPrinter;
    }

    public void execute(String[] commandLineFiles) {
        addParsersPool(commandLineFiles);
        setCountDownLatch();
        parsersPool.forEach(parseService::execute);
        writer.execute(ordersPrinter);
        writer.shutdown();
        parseService.shutdown();
    }

    private void addParsersPool(String[] files){
        Arrays.stream(files).forEach(file -> {
            try {
                OrdersRunnableIO ordersRunnableIO = fileParser.parse(file);
                ordersRunnableIO.setFile(file);
                parsersPool.add(ordersRunnableIO);
            } catch (NoSuchFieldException ex) {
                LOGGER.error("Not found methods for parsing this file: "+file);
            }
        });
    }

    private void setCountDownLatch(){
        int filesCount = parsersPool.size();
        countDownLatch = new CountDownLatch(filesCount);
        parsersPool.forEach(parser -> parser.setCountDownLatch(countDownLatch));
        ordersPrinter.setCountDownLatch(countDownLatch);
    }
}
