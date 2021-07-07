create table products_shop.products(
    id          serial not null,
    name        varchar(64),
    description varchar(1000),
    cost        float
   );