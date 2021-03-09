package executors;

import main.ExecutorsParsers;
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

@Service("executorsParsers")
class ExecutorsParsersImpl implements ExecutorsParsers {

    private static final Logger LOGGER = LogManager.getLogger(ExecutorsParsersImpl.class.getName());

    // защелка
    private CountDownLatch countDownLatch;
    private final ExecutorService writer;
    private final ExecutorService parseService;
    private final Queue<OrdersIO> parsersPool;
    private OrdersIO orderWriter;
    private FileParser fileParser;

    public ExecutorsParsersImpl(){
        parsersPool = new ConcurrentLinkedQueue<>();
        writer = Executors.newSingleThreadExecutor();
        parseService = Executors.newCachedThreadPool();
    }

    @Autowired
    public void setFileParser(FileParser fileParser){
        this.fileParser = fileParser;
    }

    @Autowired
    @Qualifier("ordersWriter")
    public void setOrdersWriter(OrdersIO orderWriter){
        this.orderWriter = orderWriter;
    }

    public void execute(String[] commandLineFiles) {
        addParsersPool(commandLineFiles);
        setCountDownLatch();
        parsersPool.forEach(parseService::execute);
        writer.execute(orderWriter);
        writer.shutdown();
        parseService.shutdown();
    }

    private void addParsersPool(String[] files){
        Arrays.stream(files).forEach(file -> {
            try {
                OrdersIO ordersIO = fileParser.parse(file);
                ordersIO.setFile(file);
                parsersPool.add(ordersIO);
            } catch (NoSuchFieldException ex) {
                LOGGER.error("Not found methods for parsing this file: "+file);
                // TODO убрать !!!
                //System.err.println("Not found methods for parsing this file: "+file);
            }
        });
    }

    private void setCountDownLatch(){
        int filesCount = parsersPool.size();
        countDownLatch = new CountDownLatch(filesCount);
        parsersPool.forEach(parser -> parser.setCountDownLatch(countDownLatch));
        orderWriter.setCountDownLatch(countDownLatch);
    }
}
