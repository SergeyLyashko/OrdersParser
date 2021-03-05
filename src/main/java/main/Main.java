package main;

import configuration.OrdersParserConfiguration;
import jsonhandlers.CsvParser;
import jsonhandlers.JsonParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(OrdersParserConfiguration.class);
        JsonParser jsonParser = context.getBean("jsonParser", JsonParser.class);
        //jsonParser.parse();
        CsvParser csvParser = context.getBean("csvParser", CsvParser.class);
        csvParser.parse();
        jsonParser.print();

    }
}
