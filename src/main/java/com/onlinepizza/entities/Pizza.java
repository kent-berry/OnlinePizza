package com.onlinepizza.entities;

import com.onlinepizza.models.PizzaStyle;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Pizza {
    @Id
    private UUID id;

    @Version
    private Integer version;
    private String name;
    private PizzaStyle style;
    private String upc;
    private Integer quantityAvailable;
    private BigDecimal price;
    private LocalDateTime created;
    private LocalDateTime lastUpdated;
}
