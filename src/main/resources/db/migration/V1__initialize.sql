-- PRODUCTS
create table products_shop.categories
(
    id         bigserial not null
        constraint categories_pk
            primary key,
    name       varchar(64),
    updated_at timestamp default now(),
    created_at timestamp default now()
);

create table products_shop.products
(
    id          bigserial not null
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

create table products_shop.ordered_products
(
    id                      bigserial not null
        constraint ordered_products_pk
        primary key,
    product                 integer
        constraint ordered_products_products_id_fk
        references products_shop.products,
    quantity                integer not null,
    ordered_product_name    double precision,
    total_price             double precision,
    created_at  timestamp default now(),
    updated_at  timestamp default now()
);


-- ADDRESS

create table products_shop.countries(
      id          bigserial not null constraint countries_pk primary key,
      name        varchar(64)

);
create table products_shop.regions(
      id          bigserial not null constraint regions_pk primary key,
      name        varchar(64),
      country     integer
          constraint regions_countries_id_fk
          references products_shop.countries
);

create table products_shop.cities(
      id          bigserial not null constraint cities_pk primary key,
      name        varchar(64),
      region      integer
          constraint cites_regions_id_fk
          references products_shop.regions
);

create table products_shop.addresses(
    id          bigserial not null constraint addresses_pk primary key,
    city        integer
        constraint addresses_cities_id_fk
        references products_shop.cities,
    created_at  timestamp default now(),
    updated_at  timestamp default now()
);

-- USERS

create table products_shop.users (
     id          bigserial not null constraint users_pk primary key,
     login       varchar(32) not null unique,
     password    varchar(64) not null,
     email    varchar(128) not null,
     birth_date date,
     phone_number   varchar(128),
     updated_at timestamp default now(),
     created_at timestamp default now()
);

create table products_shop.roles (
    id      bigserial not null constraint roles_pk primary key,
    name    varchar(32) not null
);

create table products_shop.users_roles (
    users_id    bigint not null,
    roles_id    int not null,
    primary key (users_id, roles_id),
    foreign key (users_id) references products_shop.users (id),
    foreign key (roles_id) references products_shop.roles (id)
);

create table products_shop.users_addresses (
       users_id    bigint not null,
       addresses_id    int not null,
       primary key (users_id, addresses_id),
       foreign key (users_id) references products_shop.users (id),
       foreign key (addresses_id) references products_shop.addresses (id)
);

-- ORDER

create table products_shop.orders (
     id          bigserial not null constraint orders_pk primary key,
     user_id        integer
         constraint orders_users_id_fk
             references products_shop.users,
     total_sum double precision,
     created_at  timestamp default now(),
     updated_at  timestamp default now()
);

create table products_shop.orders__ordered_products (
    orders_id    bigint not null,
    ordered_products_id    int not null,
    primary key (orders_id, ordered_products_id),
    foreign key (orders_id) references products_shop.orders (id),
    foreign key (ordered_products_id) references products_shop.ordered_products (id)
);

-- WAREHOUSE

create table products_shop.products_stock
(
    id          bigserial not null constraint products_stock_pk primary key,
    product     integer
        constraint products_stock_products_id_fk
        references products_shop.products,
    quantity                integer not null,
    updated_at  timestamp default now()
);

-- DELIVERY

create table products_shop.deliveries
(
    id          bigserial not null constraint deliveries_pk primary key,
    order_id    integer
        constraint deliveries_orders_id_fk
        references products_shop.orders,
    address     integer
        constraint deliveries_addresses_fk
        references products_shop.addresses,
    coast                double precision,
    created_at  timestamp default now(),
    updated_at  timestamp default now()
);

-- PAYMENTS

create table products_shop.payments
(
    id          bigserial not null constraint payments_pk primary key,
    accepted    boolean not null default false,
    type        varchar(64) not null,
    order_id    integer
        constraint payments_orders_fk
        references products_shop.orders,
    created_at  timestamp default now(),
    updated_at  timestamp default now()
);

