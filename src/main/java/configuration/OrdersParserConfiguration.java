package configuration;

import com.opencsv.CSVParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = {"handlers", "orders", "parsers", "main"})
@Configuration
public class OrdersParserConfiguration {

    @Bean
    public CSVParser csvParser(){
        return new CSVParser();
    }
}
