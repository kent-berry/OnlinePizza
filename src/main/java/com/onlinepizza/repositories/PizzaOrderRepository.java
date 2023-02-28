package com.onlinepizza.repositories;

import com.onlinepizza.entities.PizzaOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PizzaOrderRepository extends JpaRepository<PizzaOrder, UUID> {
}
