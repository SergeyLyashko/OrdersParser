package parsersfactory;

import main.FileParser;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class FileParserFactoryTest {

    @Test
    public void createParser_JsonFile() throws NoSuchFieldException {
        FileParserFactory fileParserFactoryMock = mock(FileParserFactory.class);
        FileParser fileParserMock = mock(FileParser.class);
        String jsonFile = "testJsonOrder.json";
        when(fileParserFactoryMock.createParser(jsonFile)).thenReturn(fileParserMock);
    }

    @Test
    public void createParser_CsvFile() throws NoSuchFieldException {
        FileParserFactory fileParserFactoryMock = mock(FileParserFactory.class);
        FileParser fileParserMock = mock(FileParser.class);
        String csvFile = "testCsvOrder.csv";
        when(fileParserFactoryMock.createParser(csvFile)).thenReturn(fileParserMock);
    }

    @Test
    public void createParser_OtherFile() throws NoSuchFieldException {
        FileParserFactory fileParserFactoryMock = mock(FileParserFactory.class);
        String otherFile = "testOther.test";
        when(fileParserFactoryMock.createParser(otherFile)).thenThrow(new NoSuchFieldException());
    }
}