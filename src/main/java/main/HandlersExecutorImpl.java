package main;

import json.OrdersPrinter;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Executor services of parsing files & print to stdout.
 */
@Service("handlersExecutor")
class HandlersExecutorImpl implements HandlerExecutor {

    private final ExecutorService printerService;
    private final ExecutorService parseService;

    HandlersExecutorImpl(){
        printerService = Executors.newSingleThreadExecutor();
        parseService = Executors.newCachedThreadPool();
    }

    /**
     * Execute pool of parsers file
     * @param parsersPool
     */
    @Override
    public void execute(Queue<FileParser> parsersPool) {
        parsersPool.forEach(parseService::execute);
        parseService.shutdown();
    }

    /**
     * Execute printer result parse of orders
     * @param ordersPrinter
     */
    @Override
    public void execute(OrdersPrinter ordersPrinter){
        printerService.execute(ordersPrinter);
        printerService.shutdown();
    }
}
