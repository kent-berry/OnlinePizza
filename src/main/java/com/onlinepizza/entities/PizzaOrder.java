package com.onlinepizza.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Builder
public class PizzaOrder {

    public PizzaOrder(UUID id, Long version, Timestamp created, Timestamp lastUpdated, String customerRef,
                      Customer customer, Set<PizzaOrderLine> pizzaOrderLines, PizzaOrderShipment pizzaOrderShipment) {
        this.id = id;
        this.version = version;
        this.created = created;
        this.lastUpdated = lastUpdated;
        this.customerRef = customerRef;
        this.setCustomer(customer);
        this.pizzaOrderLines = pizzaOrderLines;
        this.setPizzaOrderShipment(pizzaOrderShipment);
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false )
    private UUID id;

    @Version
    private Long version;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp created;

    @UpdateTimestamp
    private Timestamp lastUpdated;

    public boolean isNew() {
        return this.id == null;
    }

    private String customerRef;

    @ManyToOne
    private Customer customer;

    public void setCustomer(Customer customer) {
        this.customer = customer;
        customer.getPizzaOrders().add(this);
    }

    @OneToMany(mappedBy = "pizzaOrder")
    private Set<PizzaOrderLine> pizzaOrderLines;

    @OneToOne(cascade = CascadeType.PERSIST)
    private PizzaOrderShipment pizzaOrderShipment;

    public void setPizzaOrderShipment(PizzaOrderShipment pizzaOrderShipment){
        this.pizzaOrderShipment = pizzaOrderShipment;
        pizzaOrderShipment.setPizzaOrder(this);
    }

}
