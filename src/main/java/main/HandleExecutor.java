package main;

import jsonwriter.JsonOrderWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("handleExecutor")
public class HandleExecutor {

    // защелка
    private CountDownLatch countDownLatch;
    private final ExecutorService writer;
    private final ExecutorService parseService;
    private final Queue<OrdersParser> parsersPool;
    private JsonOrderWriter orderWriter;
    private FileParser fileParser;

    public HandleExecutor(){
        parsersPool = new ConcurrentLinkedQueue<>();
        writer = Executors.newSingleThreadExecutor();
        parseService = Executors.newCachedThreadPool();
    }

    @Autowired
    public void setFileParser(FileParser fileParser){
        this.fileParser = fileParser;
    }

    @Autowired
    public void setOrderWriter(JsonOrderWriter orderWriter){
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
                OrdersParser ordersParser = fileParser.parse(file);
                ordersParser.setFile(file);
                parsersPool.add(ordersParser);
            } catch (NoSuchFieldException e) {
                System.err.println("Not found methods for parsing this "+file);
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
