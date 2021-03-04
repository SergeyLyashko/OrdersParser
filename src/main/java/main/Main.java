package main;

import configuration.OrdersParserConfiguration;
import jsonhandlers.JsonParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(OrdersParserConfiguration.class);
        JsonParser jsonParser = context.getBean("ordersParser", JsonParser.class);
        jsonParser.parse();
        jsonParser.print();

    }
}
