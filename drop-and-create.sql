drop table if exists customer;
drop table if exists pizza;
create table customer (id varchar(36) not null, created datetime(6), last_updated datetime(6), name varchar(255), version integer, primary key (id)) engine=InnoDB;
create table pizza (id varchar(36) not null, created datetime(6), last_updated datetime(6), name varchar(50) not null, price decimal(38,2) not null, quantity_available integer, style smallint not null, upc varchar(255) not null, version integer, primary key (id)) engine=InnoDB;
