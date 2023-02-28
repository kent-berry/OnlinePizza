drop table if exists pizza_order_shipment;

CREATE TABLE pizza_order_shipment
(
    id                 VARCHAR(36) NOT NULL PRIMARY KEY,
    pizza_order_id            VARCHAR(36) UNIQUE,
    tracking_number    VARCHAR(50),
    created       TIMESTAMP,
    last_updated DATETIME(6) DEFAULT NULL,
    version            BIGINT      DEFAULT NULL,
    CONSTRAINT bos_pk FOREIGN KEY (pizza_order_id) REFERENCES pizza_order (id)
) ENGINE = InnoDB;

ALTER TABLE pizza_order
    ADD COLUMN pizza_order_shipment_id VARCHAR(36);

ALTER TABLE pizza_order
    ADD CONSTRAINT bos_shipment_fk
        FOREIGN KEY (pizza_order_shipment_id) REFERENCES pizza_order_shipment (id);