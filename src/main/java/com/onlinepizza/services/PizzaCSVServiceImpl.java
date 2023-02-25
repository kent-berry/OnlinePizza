package com.onlinepizza.services;

import com.onlinepizza.models.PizzaCSVRecord;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
public class PizzaCSVServiceImpl implements PizzaCSVService {
    @Override
    public List<PizzaCSVRecord> convertCSV(File file) throws FileNotFoundException {

        try {
            List<PizzaCSVRecord> pizzaCSVRecords = new CsvToBeanBuilder<PizzaCSVRecord>(new FileReader(file))
                    .withType(PizzaCSVRecord.class).build().parse();
            return pizzaCSVRecords;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
