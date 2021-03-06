package configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = {"handlers", "orders", "parsers", "main"})
@Configuration
public class OrdersParserConfiguration {
}
