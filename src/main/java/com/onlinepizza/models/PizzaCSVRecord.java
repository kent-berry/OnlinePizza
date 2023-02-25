package com.onlinepizza.models;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PizzaCSVRecord {
    @CsvBindByName
    private Integer row;
    @CsvBindByName
    private Integer id;
    @CsvBindByName
    private Integer version;
    @CsvBindByName
    private String name;
    @CsvBindByName
    private String style;
    @CsvBindByName
    private String upc;
    @CsvBindByName
    private Integer quantity;
    @CsvBindByName
    private Float price;
}
