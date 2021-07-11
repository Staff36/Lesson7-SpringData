create table products_shop.categories
(
    id         serial not null
        constraint categories_pk
            primary key,
    name       varchar(64),
    updated_at timestamp default now(),
    created_at timestamp default now()
);

create table products_shop.products
(
    id          serial not null
        constraint products_pk
            primary key,
    name        varchar(64),
    description varchar(1000),
    cost        double precision,
    category    integer
        constraint products_categories_id_fk
            references products_shop.categories,
    created_at  timestamp default now(),
    updated_at  timestamp default now()
);