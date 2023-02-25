package com.onlinepizza.services;

import com.onlinepizza.models.PizzaCSVRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface PizzaCSVService {
    List<PizzaCSVRecord> convertCSV(File file) throws FileNotFoundException;
}
