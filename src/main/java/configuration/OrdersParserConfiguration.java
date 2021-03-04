package configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = {"handlers", "jsons"})
@Configuration
public class OrdersParserConfiguration {

}
