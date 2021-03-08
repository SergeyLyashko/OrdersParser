package main;

import csvparser.CsvOrdersParser;
import jsonparser.JsonOrdersParser;
import jsonwriter.JsonOrderWriter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("fileParser")
public class FileParse implements ApplicationContextAware {

    private static final String FILE_EXTENSION_REGEX = "[\\S]+[.]([\\S]+)";
    private static final Pattern FILE_EXTENSION = Pattern.compile(FILE_EXTENSION_REGEX);
    // защелка
    private CountDownLatch countDownLatch;
    // запуск потока записи в файл
    private final ExecutorService writer;
    private final ExecutorService executorService;
    // очередь читателей файлов
    private final Queue<OrdersParser> parsersPool;
    private ApplicationContext context;
    private JsonOrderWriter orderWriter;

    @Autowired
    public void setOrderWriter(JsonOrderWriter orderWriter){
        this.orderWriter = orderWriter;
    }

    public FileParse(){
        parsersPool = new ConcurrentLinkedQueue<>();
        writer = Executors.newSingleThreadExecutor();
        executorService = Executors.newCachedThreadPool();
    }

    public void execute(String[] commandLineFiles) {
        int filesCount = commandLineFiles.length;
        countDownLatch = new CountDownLatch(filesCount);
        addParsersPool(commandLineFiles);
        parsersPool.forEach(executorService::execute);
        executorService.shutdown();
        writer.execute(orderWriter);
        writer.shutdown();
    }

    private void addParsersPool(String[] files){
        Arrays.stream(files).forEach(file -> {
            try {
                OrdersParser ordersParser = fileParser(file);
                ordersParser.setFile(file);
                parsersPool.add(ordersParser);
            } catch (NoSuchFieldException e) {
                System.err.println("Not found methods for parsing this extension ."+fileExtensionParser(file));
            }
        });
    }

    private OrdersParser fileParser(String fileName) throws NoSuchFieldException {
        String fileExtension = fileExtensionParser(fileName);
        switch (fileExtension){
            case "json":
                return context.getBean("jsonOrdersParser", JsonOrdersParser.class);
            case "csv":
                return context.getBean("csvOrdersParser", CsvOrdersParser.class);
            default:
                throw new NoSuchFieldException();
        }
    }

    private String fileExtensionParser(String fileName){
        String extension = null;
        Matcher matcher = FILE_EXTENSION.matcher(fileName);
        while (matcher.find()){
            extension = matcher.group(1);
        }
        return extension;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
