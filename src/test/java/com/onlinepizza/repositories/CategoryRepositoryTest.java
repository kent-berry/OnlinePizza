package com.onlinepizza.repositories;

import com.onlinepizza.entities.Category;
import com.onlinepizza.entities.Pizza;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    PizzaRepository pizzaRepository;

    Pizza testPizza;

    @BeforeEach
    void setUp() {
        testPizza = pizzaRepository.findAll().get(0);
    }

    @Transactional
    @Test
    void addCategory() {
        Category savedCategory = categoryRepository.save(Category.builder()
                .description("Gluten-Free")
                .build());

        testPizza.addCategory(savedCategory);
        Pizza savedPizza = pizzaRepository.save(testPizza);

        System.out.println(savedPizza.getName());
    }
}