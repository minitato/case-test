drop table if exists customers;

create table customers (
  id bigint auto_increment primary key,
  name varchar(100) not null,
  balance numeric(20, 2) not null,
  transfer_limit numeric(20, 2) not null,
  is_active boolean not null
);