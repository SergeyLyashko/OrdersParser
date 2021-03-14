package main;

import json.OrdersPrinter;
import org.junit.Test;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.mockito.Mockito.*;

public class HandlersExecutorTest {

    @Test
    public void execute_FileParserPool() {
        Queue<FileParser> parsersPool = spy(new ConcurrentLinkedQueue<>());
        HandlersExecutorImpl handlersExecutorMock = mock(HandlersExecutorImpl.class);
        doNothing().when(handlersExecutorMock).execute(parsersPool);
    }

    @Test
    public void execute_OrderPrinter() {
        OrdersPrinter mock = mock(OrdersPrinter.class);
        HandlersExecutorImpl handlersExecutorMock = mock(HandlersExecutorImpl.class);
        doNothing().when(handlersExecutorMock).execute(mock);
    }

}