package configuration;

import com.opencsv.CSVParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = {"csv", "executors", "json", "jsonIO", "main", "orders"})
@Configuration
public class ApplicationConfiguration {

    @Bean
    public CSVParser csvParser(){
        return new CSVParser();
    }
}
