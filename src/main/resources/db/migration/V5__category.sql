drop table if exists category;
drop table if exists pizza_category;

create table category
(
    id                 varchar(36) NOT NULL PRIMARY KEY,
    description        varchar(50),
    created       timestamp,
    last_updated datetime(6) DEFAULT NULL,
    version            bigint      DEFAULT NULL
) ENGINE = InnoDB;

create table pizza_category
(
    pizza_id     varchar(36) NOT NULL,
    category_id varchar(36) NOT NULL,
    primary key (pizza_id, category_id),
    constraint pc_pizza_id_fk FOREIGN KEY (pizza_id) references pizza (id),
    constraint pc_category_id_fk FOREIGN KEY (category_id) references category (id)
) ENGINE = InnoDB;