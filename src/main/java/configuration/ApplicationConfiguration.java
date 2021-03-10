package configuration;

import com.google.gson.GsonBuilder;
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

    @Bean
    public GsonBuilder gsonBuilder(){
        return new GsonBuilder();
    }
}
