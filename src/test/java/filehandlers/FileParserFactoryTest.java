package filehandlers;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileParserFactoryTest {

    @Test
    public void createParser() throws NoSuchFieldException {
        FileParserFactory fileParserFactoryMock = mock(FileParserFactory.class);
        FileParser fileParserMock = mock(FileParser.class);

        String jsonFile = "testJsonOrder.json";
        when(fileParserFactoryMock.createParser(jsonFile)).thenReturn(fileParserMock);

        String csvFile = "testCsvOrder.csv";
        when(fileParserFactoryMock.createParser(csvFile)).thenReturn(fileParserMock);

        String otherFile = "testOther.test";
        when(fileParserFactoryMock.createParser(otherFile)).thenThrow(new NoSuchFieldException());
    }
}