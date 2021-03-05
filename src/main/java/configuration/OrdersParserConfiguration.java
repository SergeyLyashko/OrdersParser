package configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = {"jsonhandlers", "orders"})
@Configuration
public class OrdersParserConfiguration {
}
