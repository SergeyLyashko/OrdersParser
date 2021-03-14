package json;

import main.FileParser;
import main.ParserFactory;
import main.ParsersExecutor;
import org.junit.Test;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class JsonFileParserTest {

    @Test
    public void run() {
        JsonFileParser jsonFileParserMock = mock(JsonFileParser.class);
        ParsersExecutor spy = spy(new ParsersExecutor());
        Queue<FileParser> parsersPool = new ConcurrentLinkedQueue<>();
        parsersPool.add(jsonFileParserMock);
        spy.execute(parsersPool);
        verify(jsonFileParserMock).run();
    }
}