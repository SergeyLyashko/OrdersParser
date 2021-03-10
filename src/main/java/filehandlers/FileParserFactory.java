package filehandlers;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("parserFactory")
class FileParserFactory implements ApplicationContextAware {

    private static final String FILE_EXTENSION_REGEX = "[\\S]+[.]([\\S]+)";
    private static final Pattern FILE_EXTENSION_PATTERN = Pattern.compile(FILE_EXTENSION_REGEX);
    private ApplicationContext context;

    /**
     * Create a bean of parse handler for the specified file extension
     * @param fileName parsed file
     * @return interface implementation of parser handler
     * @throws NoSuchFieldException
     */
    FileParser createParser(String fileName) throws NoSuchFieldException {
        String fileExtension = defineFileExtension(fileName);
        switch (fileExtension){
            case "json":
                return context.getBean("jsonFileParser", FileParser.class);
            case "csv":
                return context.getBean("csvFileParser", FileParser.class);
            default:
                throw new NoSuchFieldException();
        }
    }

    /*
     * Defined extension of file for bean creator
     */
    private String defineFileExtension(String fileName){
        String extension = null;
        Matcher matcher = FILE_EXTENSION_PATTERN.matcher(fileName);
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
