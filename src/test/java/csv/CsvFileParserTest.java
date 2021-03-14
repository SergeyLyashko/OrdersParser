package csv;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.mockito.Mockito.*;

public class CsvFileParserTest {

    @Test
    public void run() {
        CsvFileParser csvFileParserMock = mock(CsvFileParser.class);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(csvFileParserMock);
        verify(csvFileParserMock).run();
    }
}