package main;

import configuration.ApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import picocli.CommandLine;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        CommandLineArgsHandler commandLine = context.getBean("commandLine", CommandLineArgsHandler.class);
        new CommandLine(commandLine).execute(args);
    }
}
