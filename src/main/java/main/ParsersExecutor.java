package main;

import json.OrdersPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Executor services of parsing files & print to stdout.
 */
@Service("parsersExecutor")
class ParsersExecutor {

    private CountDownLatch countDownLatch;
    private final ExecutorService printerService;
    private final ExecutorService parseService;
    private OrdersPrinter ordersPrinter;

    public ParsersExecutor(){
        printerService = Executors.newSingleThreadExecutor();
        parseService = Executors.newCachedThreadPool();
    }

    @Autowired
    public void setOrdersPrinter(OrdersPrinter ordersPrinter){
        this.ordersPrinter = ordersPrinter;
    }

    /**
     * Execute pool of handlers file with count down latch
     * for threads of parsers & thread of printer result
     * @param parsersPool
     */
    void execute(Queue<FileParser> parsersPool) {
        setCountDownLatch(parsersPool);
        parsersPool.forEach(parseService::execute);
        printerService.execute(ordersPrinter);
        printerService.shutdown();
        parseService.shutdown();
    }

    private void setCountDownLatch(Queue<FileParser> parsersPool){
        int filesCount = parsersPool.size();
        countDownLatch = new CountDownLatch(filesCount);
        parsersPool.forEach(parser -> parser.setCountDownLatch(countDownLatch));
        ordersPrinter.setCountDownLatch(countDownLatch);
    }
}
