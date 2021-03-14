package main;

import org.junit.Test;
import picocli.CommandLine;

import static org.mockito.Mockito.*;

public class CommandLineArgsHandlerTest {

    @Test
    public void run() {
        CommandLineArgsHandler commandLineArgsHandlerMock = mock(CommandLineArgsHandler.class);
        CommandLine commandLineSpy = spy(new CommandLine(commandLineArgsHandlerMock));
        commandLineSpy.execute("test1", "test2");
        verify(commandLineArgsHandlerMock).run();
    }
}