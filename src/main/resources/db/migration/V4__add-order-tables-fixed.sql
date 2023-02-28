drop table if exists pizza_order_line;
drop table if exists pizza_order;

CREATE TABLE `pizza_order`
(
    id                 varchar(36) NOT NULL,
    created            datetime(6)  DEFAULT NULL,
    customer_ref       varchar(255) DEFAULT NULL,
    last_updated       datetime(6)  DEFAULT NULL,
    version            bigint       DEFAULT NULL,
    customer_id        varchar(36)  DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FOREIGN KEY (customer_id) REFERENCES customer (id)
) ENGINE = InnoDB;

CREATE TABLE `pizza_order_line`
(
    id                 varchar(36) NOT NULL,
    pizza_id           varchar(36) DEFAULT NULL,
    created            datetime(6) DEFAULT NULL,
    last_updated       datetime(6) DEFAULT NULL,
    order_quantity     int         DEFAULT NULL,
    quantity_allocated int         DEFAULT NULL,
    version            bigint      DEFAULT NULL,
    pizza_order_id     varchar(36) DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FOREIGN KEY (pizza_order_id) REFERENCES pizza_order (id),
    CONSTRAINT FOREIGN KEY (pizza_id) REFERENCES pizza (id)
) ENGINE = InnoDB;