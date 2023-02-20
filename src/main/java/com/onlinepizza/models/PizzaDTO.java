package com.onlinepizza.models;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class PizzaDTO {
    private UUID id;
    private Integer version;
    private String name;
    private PizzaStyle style;
    private String upc;
    private Integer quantityAvailable;
    private BigDecimal price;
    private LocalDateTime created;
    private LocalDateTime lastUpdated;

}
