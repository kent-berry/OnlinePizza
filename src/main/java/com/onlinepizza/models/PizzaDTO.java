package com.onlinepizza.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank
    @NotNull
    private String name;

    @NotNull
    private PizzaStyle style;

    @NotBlank
    @NotNull
    private String upc;
    private Integer quantityAvailable;

    @NotNull
    private BigDecimal price;
    private LocalDateTime created;
    private LocalDateTime lastUpdated;

}
