package main;

import csvparser.CsvOrdersParser;
import jsonparser.JsonOrdersParser;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("fileParser")
public class FileParser implements ApplicationContextAware {

    private static final String FILE_EXTENSION_REGEX = "[\\S]+[.]([\\S]+)";
    private static final Pattern FILE_EXTENSION = Pattern.compile(FILE_EXTENSION_REGEX);
    private ApplicationContext context;

    public OrdersParser parse(String fileName) throws NoSuchFieldException {
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
