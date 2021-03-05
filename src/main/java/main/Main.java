package main;

import configuration.OrdersParserConfiguration;
import handlers.OrdersPack;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import picocli.CommandLine;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(OrdersParserConfiguration.class);
        CommandLineFileNameArgs commandLine = context.getBean("commandLine", CommandLineFileNameArgs.class);
        new CommandLine(commandLine).execute("orders.json", "orders.csv");
        OrdersPack ordersPack = context.getBean("ordersPack", OrdersPack.class);
        ordersPack.print();
    }
}
