package com.onlinepizza.entities;

import com.onlinepizza.models.PizzaStyle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Pizza {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Integer version;

    @NotBlank
    @NotNull
    @Size(max = 50)
    @Column(length = 50)
    private String name;

    @NotNull
    private PizzaStyle style;

    @NotNull
    @NotBlank
    @Size(max = 255)
    private String upc;
    private Integer quantityAvailable;

    @NotNull
    private BigDecimal price;

    @OneToMany(mappedBy = "pizza")
    private Set<PizzaOrderLine> pizzaOrderLines;

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "pizza_category", joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public void addCategory(Category category){
        this.categories.add(category);
        category.getPizzas().add(this);
    }

    public void removeCategory(Category category){
        this.categories.remove(category);
        category.getPizzas().remove(this);
    }

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime lastUpdated;
}
