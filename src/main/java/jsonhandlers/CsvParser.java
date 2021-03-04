package jsonhandlers;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CsvParser {

    // TODO test
    private String filePath = "orders.csv";

    public void parse() {
        BufferedReader reader = reader(filePath);
        if(reader != null){
            CSVReader csvReader = new CSVReader(reader);
            CsvToBean<Order> orders = buildOrder(csvReader);
            orders.parse();
        }
    }

    private CsvToBean<Order> buildOrder(CSVReader csvReader) {
        CsvToBeanBuilder<Order> beanBuilder = new CsvToBeanBuilder<>(csvReader);
        return beanBuilder.build();
    }

    private BufferedReader reader(String filePath) {
        try {
            return Files.newBufferedReader(Paths.get(filePath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
