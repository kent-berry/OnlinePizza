package com.onlinepizza.services;

import com.onlinepizza.models.PizzaCSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PizzaCSVServiceImplTest {

    PizzaCSVService pizzaCSVService = new PizzaCSVServiceImpl();

    @Test
    void convertCSV() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:csvdata/pizzas.csv");

        List<PizzaCSVRecord> records = pizzaCSVService.convertCSV(file);

        System.out.println(records.size());

        assertThat(records.size()).isGreaterThan(0);
    }
}