package filehandlers;

import json.OrdersPrinter;
import main.ParsersExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Executor services of parsing files & print to stdout.
 */
@Service("parsersExecutor")
class ParsersFactoryExecutor implements ParsersExecutor {

    private static final Logger LOGGER = LogManager.getLogger(ParsersFactoryExecutor.class.getName());
    private CountDownLatch countDownLatch;
    private final ExecutorService printerService;
    private final ExecutorService parseService;
    private final Queue<FileParser> parsersPool;
    private OrdersPrinter ordersPrinter;
    private FileParserFactory fileParserFactory;

    public ParsersFactoryExecutor(){
        parsersPool = new ConcurrentLinkedQueue<>();
        printerService = Executors.newSingleThreadExecutor();
        parseService = Executors.newCachedThreadPool();
    }

    @Autowired
    public void setFileParser(FileParserFactory fileParserFactory){
        this.fileParserFactory = fileParserFactory;
    }

    @Autowired
    public void setOrdersPrinter(OrdersPrinter ordersPrinter){
        this.ordersPrinter = ordersPrinter;
    }

    /**
     * Execute pool of handlers file with count down latch
     * for threads of parsers & thread of printer result
     * @param filesFromCommandLine
     */
    public void execute(String[] filesFromCommandLine) {
        addParsersPool(filesFromCommandLine);
        setCountDownLatch();
        parsersPool.forEach(parseService::execute);
        printerService.execute(ordersPrinter);
        printerService.shutdown();
        parseService.shutdown();
    }

    private void addParsersPool(String[] files){
        Arrays.stream(files).forEach(file -> {
            try {
                FileParser fileParser = fileParserFactory.createParser(file);
                fileParser.setFile(file);
                parsersPool.add(fileParser);
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
