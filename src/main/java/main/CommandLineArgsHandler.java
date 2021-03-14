package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Class for args from command line & runnable handler.
 * Based on Picocli lib.
 */
@Service("commandLine")
class CommandLineArgsHandler implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(CommandLineArgsHandler.class.getName());

    @CommandLine.Parameters(index = "0..*")
    private String[] files;
    private ParsersExecutor parsersExecutor;
    private ParserFactory parserFactory;

    @Autowired
    public void setParserFactory(ParserFactory parserFactory){
        this.parserFactory = parserFactory;
    }

    @Autowired
    public void setParsersExecutors(ParsersExecutor parsersExecutor){
        this.parsersExecutor = parsersExecutor;
    }

    @Override
    public void run() {
        if(files.length != 0) {
            Queue<FileParser> parsersPool = createParsersPool(files);
            parsersExecutor.execute(parsersPool);
        }
    }

    private Queue<FileParser> createParsersPool(String[] files){
        Queue<FileParser> parsersPool = new ConcurrentLinkedQueue<>();
        Arrays.stream(files).forEach(file -> {
            try {
                FileParser fileParser = parserFactory.createParser(file);
                fileParser.setParsedFile(file);
                parsersPool.add(fileParser);
            } catch (NoSuchFieldException ex) {
                LOGGER.error("Not found methods for parsing this file: "+file);
            }
        });
        return parsersPool;
    }
}
