package main;

import configuration.OrdersParserConfiguration;
import orders.OrdersPack;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import picocli.CommandLine;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(OrdersParserConfiguration.class);
        CommandLineArgs commandLine = context.getBean("commandLine", CommandLineArgs.class);
        new CommandLine(commandLine).execute("orders.json", "orders.csv");
        OrdersPack ordersPack = context.getBean("ordersPack", OrdersPack.class);
        ordersPack.print();
    }
}
