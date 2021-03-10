package main;

public interface ParserFactory {

    FileParser createParser(String fileName) throws NoSuchFieldException;
}
