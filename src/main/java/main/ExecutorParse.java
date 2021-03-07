package main;

import csvparser.CsvOrdersParser;
import jsonparser.JsonOrdersParser;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("fileParser")
public class ExecutorParse implements ApplicationContextAware {

    private static final String FILE_EXTENSION_REGEX = "[\\S]+[.]([\\S]+)";
    private static final Pattern FILE_EXTENSION = Pattern.compile(FILE_EXTENSION_REGEX);

    // запуск потока записи в файл
    private final ExecutorService writer;
    // очередь читателей файлов
    private final Queue<OrdersParser> parsersPool;
    private ApplicationContext context;

    public ExecutorParse(){
        parsersPool = new ConcurrentLinkedQueue<>();
        writer = Executors.newSingleThreadExecutor();
    }

    public void execute(String[] files) {
        ExecutorService executorService = Executors.newFixedThreadPool(files.length);
        addParsersPool(files);
        parsersPool.forEach(executorService::execute);
        executorService.shutdown();
    }

    private void addParsersPool(String[] files){
        Arrays.stream(files).forEach(file -> {
            try {
                OrdersParser ordersParser = fileParser(file);
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
                OrdersParser jsonOrdersParser = context.getBean("jsonOrdersParser", JsonOrdersParser.class);
                jsonOrdersParser.setFile(fileName);
                return jsonOrdersParser;
            case "csv":
                OrdersParser csvOrdersParser = context.getBean("csvOrdersParser", CsvOrdersParser.class);
                csvOrdersParser.setFile(fileName);
                return csvOrdersParser;
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
