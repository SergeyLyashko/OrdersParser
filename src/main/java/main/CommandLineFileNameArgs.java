package main;

import handlers.OrdersParser;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import parsers.CsvOrdersParser;
import parsers.JsonOrdersParser;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("commandLine")
public class CommandLineFileNameArgs implements Runnable, ApplicationContextAware {

    private static final String FILE_EXTENSION_REGEX = "[\\S]+[.]([\\S]+)";
    private static final Pattern FILE_EXTENSION = Pattern.compile(FILE_EXTENSION_REGEX);

    @CommandLine.Parameters(index = "0..*")
    private String[] files;
    private ApplicationContext context;

    @Override
    public void run() {
        Arrays.stream(files).forEach(file ->{
            try {
                OrdersParser ordersParser = fileParser(file);
                ordersParser.parse(file);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        });
    }

    private OrdersParser fileParser(String fileName) throws NoSuchFieldException {
        String fileExtension = fileExtensionParser(fileName);
        switch (fileExtension){
            case "json":
                return context.getBean("jsonParser", JsonOrdersParser.class);
            case "csv":
                return context.getBean("csvParser", CsvOrdersParser.class);
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
