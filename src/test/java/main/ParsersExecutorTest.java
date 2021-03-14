package main;

import org.junit.Test;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.mockito.Mockito.*;

public class ParsersExecutorTest {

    @Test
    public void execute() {
        Queue<FileParser> parsersPool = spy(new ConcurrentLinkedQueue<>());
        ParsersExecutor parsersExecutorMock = mock(ParsersExecutor.class);
        doNothing().when(parsersExecutorMock).execute(parsersPool);
    }
}