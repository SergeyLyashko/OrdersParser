package main;

import configuration.OrdersParserConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import picocli.CommandLine;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(OrdersParserConfiguration.class);
        CommandLineArgs commandLine = context.getBean("commandLine", CommandLineArgs.class);
        // TODO !!! убрать тестовую строку файлов
        new CommandLine(commandLine).execute("orders1.json", "orders2.csv", "orders2.json", "orders1.csv");
    }
}
