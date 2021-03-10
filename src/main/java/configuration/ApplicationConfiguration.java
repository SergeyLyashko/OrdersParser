package configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@ComponentScan(basePackages = {"csv", "filehandlers", "json", "main", "orders"})
@Configuration
public class ApplicationConfiguration {

    @Bean
    public CSVParser csvParser(){
        return new CSVParser();
    }

    @Bean
    @Scope("prototype")
    public GsonBuilder gsonBuilder(){
        return new GsonBuilder();
    }

    @Bean
    public Gson gson(){
        return new Gson();
    }
}
