package main;

import org.junit.Test;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ParsersExecutorTest {

    @Test
    public void execute() {
        ParsersExecutor parsersExecutorMock = mock(ParsersExecutor.class);
        ExecutorService executorServiceMock = mock(ExecutorService.class);

        Queue<FileParser> parsersPool = new ConcurrentLinkedQueue<>();
        String[] testArgs1 = {"test.json", "test.csv"};
        Arrays.stream(testArgs1).forEach(testFile ->{
            FileParser fileParserMock = mock(FileParser.class);
            parsersPool.add(fileParserMock);
        });

        parsersExecutorMock.execute(testArgs1);
        parsersPool.forEach(executorServiceMock::execute);
        verify(executorServiceMock).execute(parsersPool.element());
    }
}